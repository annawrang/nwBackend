package se.netwomen.NetWomenBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.NetworkFilter;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.TagUpdateAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.AreaTagAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.CountryTagAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.TagViewAlternative;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.alternative.AreaTagAlternativeDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.alternative.CountryTagAlternativeDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.AreaTagAlternativeRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CountryTagAlternativeRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CountryTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.ForTagRepository;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.Parsers.AlternativeParser;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;

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
    private final CountryTagAlternativeRepository countryTagAlternativeRepository;
    private final ForTagRepository forTagRepository;
    private final CountryTagRepository countryTagRepository;
    private final static Sort sortByName = new Sort(Sort.Direction.ASC, "name");

    public NetworkService(NetworkRepository networkRepository, CountryTagAlternativeRepository countryTagAlternativeRepository, ForTagRepository forTagRepository, CountryTagRepository countryTagRepository) {
        this.networkRepository = networkRepository;
        this.countryTagAlternativeRepository = countryTagAlternativeRepository;
        this.forTagRepository = forTagRepository;
        this.countryTagRepository = countryTagRepository;
    }

    public NetworkDTO saveNetwork(Network network) {
        return networkRepository.save(NetworkParser.networkToNewEntity( network,
                                                                        parseCountryTagsIfNotNull(network),
                                                                        getExistingForTagEntities(network)));
    }

    public void saveCountries(Set<CountryTagAlternative> countryTag) {
       countryTag.forEach(country ->
               countryTagAlternativeRepository.save(AlternativeParser.countryTagAlternativeToNewEntity(country)));
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

        if( !"null".equalsIgnoreCase (param.getCountry()) && !"null".equalsIgnoreCase(param.getForTag())) {
            networks = findNetworksByForTagNamesAndCountryName(param);
            if (!"null".equalsIgnoreCase(param.getArea())) {
                networks = findNetworksByAreaTagName(networks, param);
            }
            return networks;
        }
        if( !"null".equalsIgnoreCase(param.getCountry())){
            networks = findNetworksByCountryTagName(param);
            if (!"null".equalsIgnoreCase(param.getArea())) {
                networks = findNetworksByAreaTagName(networks, param);
            }
            return networks;
        }
        if(!"null".equalsIgnoreCase(param.getForTag())){
            return findNetworksByForTagNames(param);
        }
        return findAllNetworks(param);
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

    public void connectAreaTagAlternativeToCountryTagAlternative(TagUpdateAlternative countryTagUpdate) {
        countryTagAlternativeRepository.findByName(countryTagUpdate.getCountryTag())
                .map(country -> {
                    country.getAreaTagAlternativeDTOs().add(new AreaTagAlternativeDTO(null, countryTagUpdate.getAreaTag()));
                    return countryTagAlternativeRepository.save(country);})
                .orElseThrow(BadRequestException::new);
    }

    public Set<AreaTagAlternative> findAreaTagAlternativesFromCountryName(String country) {
        return countryTagAlternativeRepository.findByName(country)
                .map(tag ->
                        tag.getAreaTagAlternativeDTOs()
                                .stream()
                                .map(area ->
                                        AlternativeParser.entityToExistingAreaTagAlternative(area)).collect(Collectors.toSet()))
                .orElseThrow(BadRequestException::new);
    }

    public TagViewAlternative getAlternativeTags(){
        List<ForTagDTO> forTagDTOs = forTagRepository.findAll(sortByName);
        List<CountryTagAlternativeDTO> countryTagAlternativeDTOs = countryTagAlternativeRepository.findAll(sortByName);
        return new TagViewAlternative(AlternativeParser.parseCountryTagAlternativeDTOs(countryTagAlternativeDTOs), NetworkParser.parseForTagEntities(forTagDTOs));
    }

    public TagView getUsedTags() {
        List<ForTagDTO> forTagDTOs = forTagRepository.findAll(sortByName);
        List<CountryTagDTO> countryTagDTOs = countryTagRepository.findAll(sortByName)
                .stream()
                .filter(distinctByName(p -> p.getName()))
                .collect(Collectors.toList());
        return new TagView(NetworkParser.parseCountryTagEntities(countryTagDTOs), NetworkParser.parseForTagEntities(forTagDTOs));
    }

    private Set<CountryTagDTO> parseCountryTagsIfNotNull(Network network){
        return network.getCountryTags()
                .stream()
                .map(countryTag ->
                        NetworkParser.countryTagToNewEntity(countryTag, parseAreaTagsIfNotNull(countryTag)))
                .collect(Collectors.toSet());
    }

    private Set<AreaTagDTO> parseAreaTagsIfNotNull(CountryTag countryTag) {
        Set<AreaTagDTO> areaTagDTOs = new HashSet<>();
        if (countryTag.getAreaTags() != null) {
            areaTagDTOs = countryTag.getAreaTags()
                    .stream()
                    .map(areaTag ->
                            NetworkParser.areaTagToNewEntity(areaTag))
                    .collect(Collectors.toSet());
        }
        return areaTagDTOs;
    }

    private Set<ForTagDTO> getExistingForTagEntities(Network network){
        Set<ForTagDTO> set = new HashSet<>();
        if(network.getForTags() != null) {
            network.getForTags()
                    .forEach(tag ->
                            forTagRepository.findByName(tag.getName())
                                    .ifPresent(country -> set.add(country)));
        }
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
        List<Network> toList = Arrays.asList(network);
        return toList;

    }
}
