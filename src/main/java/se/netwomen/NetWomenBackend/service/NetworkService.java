package se.netwomen.NetWomenBackend.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.NetworkFilter;
import se.netwomen.NetWomenBackend.model.data.network.NetworkForm;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CountryTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.ForTagRepository;
import se.netwomen.NetWomenBackend.controller.param.NetworkParam;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.user.UserRepository;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;
import se.netwomen.NetWomenBackend.service.logic.NetworkLogic;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class NetworkService {
    private final NetworkRepository networkRepository;
    private final ForTagRepository forTagRepository;
    private final CountryTagRepository countryTagRepository;
    private final UserRepository userRepository;
    private final static Sort sortByName = new Sort(Sort.Direction.ASC, "name");
    private final NetworkLogic networkLogic;

    public NetworkService(NetworkRepository networkRepository, ForTagRepository forTagRepository, CountryTagRepository countryTagRepository, UserRepository userRepository, NetworkLogic networkLogic) {
        this.networkRepository = networkRepository;
        this.forTagRepository = forTagRepository;
        this.countryTagRepository = countryTagRepository;
        this.userRepository = userRepository;
        this.networkLogic = networkLogic;
    }

    public NetworkDTO saveNetworkForm(NetworkForm network) {
        NetworkForm networkForm = networkLogic.addLocationIfNetworkIsGlobal(network);
        networkForm = networkLogic.checkIfImgExists(networkForm);
        return networkRepository.save(NetworkParser.networkToNewEntity( networkForm,
                                                                        networkLogic.parseCountryTagsIfNotNull(networkForm),
                                                                        networkLogic.parseForTagsIfNotNull(networkForm)));
    }

    public List<Network> getNetwork(String networkNumber) {
        Network network = networkRepository.findByNetworkNumber(networkNumber)
                .map(networkDTO ->
                        NetworkParser.entityToNetwork(networkDTO,
                                NetworkParser.parseCountryTagEntities(networkDTO.getCountryTags()),
                                NetworkParser.parseForTagEntities(networkDTO.getForTags())))
                .orElseThrow(BadRequestException::new);
        return Arrays.asList(network);
    }

    public Set<Network> getAllNetworks(String userNumber, int page, int size, String country, String area, String forTag) {
        List<Network> networks;
        if(networkLogic.paramHasValue(country) && networkLogic.paramHasValue(forTag)) {
            networks = networkLogic.findNetworksByForTagNamesAndCountryName(getPageRequest(page, size), forTag, country);
            if (networkLogic.paramHasValue(area)) {
                networks = networkLogic.findNetworksByAreaTagName(networks, area);
            }
            return networkLogic.changeNetworkToTrueIfUserHasItInMyNetworks(networks, userNumber);
        }
        if(networkLogic.paramHasValue(country)){
            networks = networkLogic.findNetworksByCountryTagName(getPageRequest(page, size), country);
            if (networkLogic.paramHasValue(area)) {
                networks = networkLogic.findNetworksByAreaTagName(networks, area);
            }
            return networkLogic.changeNetworkToTrueIfUserHasItInMyNetworks(networks, userNumber);
        }
        if(networkLogic.paramHasValue(forTag)){
            networks = networkLogic.findNetworksByForTagNames(getPageRequest(page, size), forTag);
            return networkLogic.changeNetworkToTrueIfUserHasItInMyNetworks(networks, userNumber);
        }
        networks = networkLogic.findAllNetworks(getPageRequest(page, size));
        return networkLogic.changeNetworkToTrueIfUserHasItInMyNetworks(networks, userNumber);
    }

    public List<NetworkFilter> getNetworksFilterSearchAutoSuggest(String search){
            return networkLogic.filterAndConcatSearchAutosuggestReults(search.toLowerCase());
    }

    public ForTagDTO saveForTag(ForTag forTag) {
        networkLogic.validateForTagDontAlreadyExist(forTag);
        return forTagRepository.save(NetworkParser.forTagtoNewEntity(forTag));
    }



    public Set<AreaTag> findAreaTagsFromCountryName(String country) {
      /* return countryTagRepository.findByName(country)
                .map(countryDTO ->
                        countryDTO.getAreaTagDTOs()
                        .stream()
                        .map(area ->
                                NetworkParser.entityToExsistingAreaTag(area))
                        .collect(Collectors.toSet()))
                .orElseThrow(BadRequestException::new);
                */
        List<CountryTagDTO> countryTagDTOs = countryTagRepository.findAllByName(country);
        Set<AreaTag> areaTags = new HashSet<>();
        for (CountryTagDTO countryTagDTO : countryTagDTOs) {
            countryTagDTO.getAreaTagDTOs()
                    .forEach(area -> areaTags.add(NetworkParser.entityToExsistingAreaTag(area)));
        }
        return areaTags.stream().filter(distinctByName(p -> p.getName())).collect(Collectors.toSet());
    }

    public List<ForTag> getForTags(){
        return forTagRepository.findAll(sortByName).stream()
                .map(NetworkParser::entityToExsistingForTag)
                .collect(Collectors.toList());
    }
    public List<CountryTag> getCountryTags() {
         List<CountryTagDTO> countryTagDtos = countryTagRepository.findAll(sortByName)
                .stream()
                .filter(distinctByName(p -> p.getName()))
                .collect(Collectors.toList());
         return  NetworkParser.parseCountryTagEntities(countryTagDtos);
    }

    public TagView getUsedTags() {
        return new TagView(getCountryTags(), getForTags());
    }

    private static <T> Predicate<T> distinctByName(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    public void addNetworkToUser(String userNumber, Network network) {
        UserDTO userDTO = userRepository.findOneWithNetworkDTOsByUserNumber(userNumber)
                .orElseThrow(NotFoundException::new);
        NetworkDTO networkDTO = networkRepository.findByNetworkNumber(network.getNetworkNumber())
                .orElseThrow(NotFoundException::new);
        userDTO.addNetworkDTO(networkDTO);
        userRepository.save(userDTO);
    }

    public List<Network> findMyNetworksForUser(String userNumber, int page, int size){
        Page<NetworkDTO> networkDTOs = networkRepository.findByUsersUserNumber(userNumber, getPageRequest(page, size));
        return NetworkParser.parseNetworkEntities(networkDTOs.getContent());
    }

    private PageRequest getPageRequest(int page, int size){
        return PageRequest.of(page, size);
    }

    public void deleteForTag(String name) {
        ForTagDTO forTagDTO = forTagRepository.findByName(name).orElseThrow(NotFoundException::new);
        List<NetworkDTO> networks = networkRepository.findByForTags(forTagDTO);
        for (NetworkDTO networkDTO: networks){
            for (ForTagDTO forTag: networkDTO.getForTags()){
                if(forTag.getName().equalsIgnoreCase(forTagDTO.getName())){
                    networkDTO.removeForTag(forTag);
                }
            }
        }
        forTagRepository.delete(forTagDTO);
    }

    public void updateNetwork(String networkNumber, NetworkForm networkForm) {
        NetworkDTO network = networkRepository.findByNetworkNumber(networkNumber)
                .map(networkDTO -> NetworkParser.networkToUpdateEntity(networkDTO, networkForm, networkLogic.parseCountryTagsIfNotNull(networkForm),
                        networkLogic.parseForTagsIfNotNull(networkForm)))
                .orElseThrow(NotFoundException::new);
        networkRepository.save(network);

    }
}
