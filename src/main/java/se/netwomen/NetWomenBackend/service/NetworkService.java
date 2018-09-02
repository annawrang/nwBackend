package se.netwomen.NetWomenBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.NetworkFilter;
import se.netwomen.NetWomenBackend.model.data.network.NetworkForm;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CountryTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.ForTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
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
    private static final String DEFAULT_IMG= "http://www.st-damien.com/uploads/8/0/4/0/80408348/person-1824147-1280_12_orig.png";

    public NetworkService(NetworkRepository networkRepository, ForTagRepository forTagRepository, CountryTagRepository countryTagRepository) {
        this.networkRepository = networkRepository;
        this.forTagRepository = forTagRepository;
        this.countryTagRepository = countryTagRepository;
    }

    public NetworkDTO saveNetwork(NetworkForm network) {
        NetworkForm networkForm = addLocationIfNetworkIsGlobal(network);
        networkForm = checkIfImgExists(networkForm);
        return networkRepository.save(NetworkParser.networkToNewEntity( networkForm,
                                                                        parseCountryTagsIfNotNull(networkForm),
                                                                        parseForTagsIfNotNull(networkForm)));
    }

    private NetworkForm checkIfImgExists(NetworkForm network) {
        if(network.getPictureUrl().equals("")){
            network.setPictureUrl(DEFAULT_IMG);
            return network;
        }
        return network;
    }

    private NetworkForm addLocationIfNetworkIsGlobal(NetworkForm network) {
        if(network.isGlobal()){
            network.getLocations().add(new Location("Global", null));
        }
        return network;
    }

    public ForTagDTO saveForTag(ForTag forTag) {
        validateForTagDontExist(forTag);
        return forTagRepository.save(NetworkParser.forTagtoNewEntity(forTag));
    }

    public List<NetworkFilter> getNetworksFilterResults(NetworkParam param){
        if(!"null".equalsIgnoreCase(param.getSearchText())){
            String searchText = param.getSearchText().toLowerCase();
            List<NetworkDTO> networkDTOS = networkRepository.findAll();
            List<NetworkFilter> networkFilters = Stream.concat(filterByNames(networkDTOS, searchText).stream(),
                            filterByDescriptions(networkDTOS, searchText).stream())
                    .collect(Collectors.toList());
            filterByForTagName(searchText)
                    .ifPresent(tag ->
                            networkFilters.add(tag));
            return networkFilters;
        }
        return null;
    }

    private Optional<NetworkFilter> filterByForTagName(String searchText){
        return forTagRepository.findByNameStartingWithIgnoreCase(searchText)
                .map(forTag ->
                        NetworkParser.entityToNetworkFilter(null, forTag.getName(),
                                "Tag"));
    }

    private List<NetworkFilter> filterByNames(List<NetworkDTO> networkDTOS, String searchText){
        return networkDTOS
                .stream()
                .filter(network ->
                        network.getName().toLowerCase().contains(searchText))
                .map(net -> NetworkParser.entityToNetworkFilter(net, net.getName(), "Name"))
                .collect(Collectors.toList());
    }

    private List<NetworkFilter> filterByDescriptions(List<NetworkDTO> networkDTOS, String searchText){
         return networkDTOS
                .stream()
                .filter(network ->
                        network.getDescription().toLowerCase().contains(searchText))
                .map(net ->
                        NetworkParser.entityToNetworkFilter(net, net.getDescription(), "Description"))
                .collect(Collectors.toList());
    }

    public List<Network> getNetworks(NetworkParam param) {
        List<Network> networks;
        if(countryParamHasValue(param) && forTagParamHasValue(param)) {
            networks = findNetworksByForTagNamesAndCountryName(param);
            if (areaParamHasValue(param)) {
                networks = findNetworksByAreaTagName(networks, param);
            }
            return networks;
        }
        if(countryParamHasValue(param)){
            networks = findNetworksByCountryTagName(param);
            if (areaParamHasValue(param)) {
                networks = findNetworksByAreaTagName(networks, param);
            }
            return networks;
        }
        if(forTagParamHasValue(param)){
            return findNetworksByForTagNames(param);
        }
        return findAllNetworks(param);
    }
    
    private boolean countryParamHasValue(NetworkParam param){
        return !(param.getCountry() == null || param.getCountry().equals("null"));
    }

    private boolean forTagParamHasValue(NetworkParam param){
        return !(param.getForTag() == null || param.getForTag().equals("null") ||  param.getForTag().equals(""));
    }

    private boolean areaParamHasValue(NetworkParam param){
        return !(param.getArea() == null|| param.getArea().equals("null"));
    }

    private List<Network> findNetworksByForTagNames(NetworkParam param){
        Page<NetworkDTO> networkDTOPage = networkRepository.findByForTagsName(param.getForTag(), getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
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

    private List<Network> findAllNetworks(NetworkParam param) {
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
        List<ForTagDTO> forTags = forTagRepository.findAll(sortByName);
        return forTags.stream()
                .map(forTagDTO ->
                        NetworkParser.entityToExsistingForTag(forTagDTO))
                .collect(Collectors.toList());
    }

    public TagView getUsedTags() {
        List<ForTagDTO> forTagDTOs = forTagRepository.findAll(sortByName);
        List<CountryTagDTO> countryTagDTOs = countryTagRepository.findAll(sortByName);
        countryTagDTOs = countryTagDTOs
                .stream()
                .filter(distinctByName(p -> p.getName()))
                .collect(Collectors.toList());
        return new TagView(NetworkParser.parseCountryTagEntities(countryTagDTOs), NetworkParser.parseForTagEntities(forTagDTOs));
    }

    private Set<CountryTagDTO> parseCountryTagsIfNotNull(NetworkForm network){
        return Optional.ofNullable(network.getLocations())
                .orElseGet(Collections::emptySet)
                .stream()
                .map(countryTag ->
                        NetworkParser.locationToEntity(countryTag))
                .collect(Collectors.toSet());

    }

    private Set<ForTagDTO> parseForTagsIfNotNull(NetworkForm network){
        Set<ForTagDTO> set = new HashSet<>();
            Optional.ofNullable(network.getForTags())
                    .orElseGet(Collections::emptySet)
                    .forEach(tag ->
                            forTagRepository.findByName(tag.getName())
                                    .ifPresent(country -> set.add(country)));
       return set;
    }

    private void validateForTagDontExist(ForTag forTag) {
        forTagRepository.findByName(forTag.getName())
                .ifPresent(forTagDTO ->  new BadRequestException());
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
