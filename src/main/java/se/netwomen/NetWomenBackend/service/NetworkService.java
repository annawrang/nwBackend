package se.netwomen.NetWomenBackend.service;

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
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;
import se.netwomen.NetWomenBackend.service.logic.NetworkLogic;

import javax.ws.rs.BadRequestException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NetworkService {
    private final NetworkRepository networkRepository;
    private final ForTagRepository forTagRepository;
    private final CountryTagRepository countryTagRepository;
    private final static Sort sortByName = new Sort(Sort.Direction.ASC, "name");
    private final NetworkLogic networkLogic;

    public NetworkService(NetworkRepository networkRepository, ForTagRepository forTagRepository, CountryTagRepository countryTagRepository, NetworkLogic networkLogic) {
        this.networkRepository = networkRepository;
        this.forTagRepository = forTagRepository;
        this.countryTagRepository = countryTagRepository;
        this.networkLogic = networkLogic;
    }

    public NetworkDTO saveNetwork(NetworkForm network) {
        NetworkForm networkForm = networkLogic.addLocationIfNetworkIsGlobal(network);
        networkForm = networkLogic.checkIfImgExists(networkForm);
        return networkRepository.save(NetworkParser.networkToNewEntity( networkForm,
                                                                        parseCountryTagsIfNotNull(networkForm),
                                                                        parseForTagsIfNotNull(networkForm)));
    }





    public ForTagDTO saveForTag(ForTag forTag) {
        networkLogic.validateForTagDontExist(forTag);
        return forTagRepository.save(NetworkParser.forTagtoNewEntity(forTag));
    }

    public List<NetworkFilter> getNetworksDropDownFilterResults(NetworkParam param){
        if(!"null".equalsIgnoreCase(param.getSearchText())){
            String searchText = param.getSearchText().toLowerCase();
            return networkLogic.filterAndConcatSearchReults(searchText);
        }
        return null;
    }



    public Set<Network> getNetworks(NetworkParam param, String userNumber) {
        List<Network> networks;
        if(networkLogic.countryParamHasValue(param) && networkLogic.forTagParamHasValue(param)) {
            networks = findNetworksByForTagNamesAndCountryName(param);
            if (networkLogic.areaParamHasValue(param)) {
                networks = findNetworksByAreaTagName(networks, param);
            }
            return changeNetworkToTrueIfUserHasItInMyNetworks(networks, userNumber);
        }
        if(networkLogic.countryParamHasValue(param)){
            networks = findNetworksByCountryTagName(param);
            if (networkLogic.areaParamHasValue(param)) {
                networks = findNetworksByAreaTagName(networks, param);
            }
            return changeNetworkToTrueIfUserHasItInMyNetworks(networks, userNumber);
        }
        if(networkLogic.forTagParamHasValue(param)){
            networks = findNetworksByForTagNames(param);
            return changeNetworkToTrueIfUserHasItInMyNetworks(networks, userNumber);
        }

        networks = findAllNetworks(param, userNumber);
        return changeNetworkToTrueIfUserHasItInMyNetworks(networks, userNumber);
    }
    


    private List<Network> findNetworksByForTagNames(NetworkParam param){
        Page<NetworkDTO> networkDTOPage = networkRepository.findByForTagsName(param.getForTag(), getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
    }

    private Set<Network> changeNetworkToTrueIfUserHasItInMyNetworks(List<Network> networks, String userNumber) {
        List<Network> myNetworksForUser = findMyNetworksForUser(userNumber);
        Set<Network> customSet = new HashSet<>();
        for (Network network : networks) {
            if(myNetworksForUser.contains(network)){
                network.setMyNetwork(true); //if duplicate
                customSet.add(network);
            } else {
                network.setMyNetwork(false); // if not duplicate
                customSet.add(network);
            }
        }
        return customSet;
    }

    private List<Network> findMyNetworksForUser(String usernumber) {
        List<NetworkDTO> myNetworkDTOs = networkRepository.findByUsersUserNumber(usernumber);
        return NetworkParser.parseNetworkEntities(myNetworkDTOs);
    }

    private List<Network> findNetworksByForTagNamesAndCountryName(NetworkParam param){
        Page<NetworkDTO> networkDTOPage = networkRepository.findDistinctByForTagsNameInAndCountryTagsName(param.getForTag(), param.getCountry(), getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
    }

    private List<Network> findNetworksByAreaTagName(List<Network> networks, NetworkParam param) {
        return networks
            .stream()
            .filter(network -> network.getCountryTags().stream()
                    .flatMap(countryTag ->  countryTag.getAreaTags().stream())
                    .anyMatch(areaTag -> areaTag.getName().equalsIgnoreCase(param.getArea())))
            .collect(Collectors.toList());
    }

    private List<Network> findAllNetworks(NetworkParam param, String userNumber) {
        Page<NetworkDTO> networkDTOPage = networkRepository.findAll(getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
    }

    private List<Network> findNetworksByCountryTagName(NetworkParam param) {
        Page<NetworkDTO> networkDTOPage = networkRepository.findByCountryTagsName(param.getCountry(), getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
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

    private Set<CountryTagDTO> parseCountryTagsIfNotNull(NetworkForm network){
        return Optional.ofNullable(network.getLocations())
                .orElseGet(Collections::emptySet)
                .stream()
                .map(NetworkParser::locationToEntity)
                .collect(Collectors.toSet());
    }

    private Set<ForTagDTO> parseForTagsIfNotNull(NetworkForm network){
        Set<ForTagDTO> set = new HashSet<>();
            Optional.ofNullable(network.getForTags())
                    .orElseGet(Collections::emptySet)
                    .forEach(tag ->
                            forTagRepository.findByName(tag.getName())
                                    .ifPresent(forTag -> set.add(forTag)));
       return set;
    }



    private static <T> Predicate<T> distinctByName(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private PageRequest getPageRequest(NetworkParam pageParam){
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
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

}
