package se.netwomen.NetWomenBackend.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.TagUpdateAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.AreaTagAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.CountryTagAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.TagViewAlternative;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.AreaTagAlternativeRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CountryTagAlternativeRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CountryTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.ForTagRepository;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;

import javax.ws.rs.BadRequestException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class NetworkService {
    private final NetworkRepository networkRepository;
    private final CountryTagAlternativeRepository countryTagAlternativeRepository;
    private final AreaTagAlternativeRepository areaTagAlternativeRepository;
    private final ForTagRepository forTagRepository;
    private final CountryTagRepository countryTagRepository;
    private final static Sort sortByName = new Sort(Sort.Direction.ASC, "name");

    public NetworkService(NetworkRepository networkRepository, CountryTagAlternativeRepository countryTagAlternativeRepository, AreaTagAlternativeRepository areaTagAlternativeRepository, ForTagRepository forTagRepository, CountryTagRepository countryTagRepository) {
        this.networkRepository = networkRepository;
        this.countryTagAlternativeRepository = countryTagAlternativeRepository;
        this.areaTagAlternativeRepository = areaTagAlternativeRepository;
        this.forTagRepository = forTagRepository;
        this.countryTagRepository = countryTagRepository;
    }


    public void saveCountries(Set<CountryTagAlternative> countryTag) {
       countryTag.forEach(country -> countryTagAlternativeRepository.save(NetworkParser.countryTagAlternativeToNewEntity(country)));
    }

    public ForTagDTO saveForTag(ForTag forTag) {
        forTagRepository.findByName(forTag.getName())
                .ifPresent(s -> {throw new BadRequestException();});
        return forTagRepository.save(NetworkParser.forTagtoNewEntity(forTag));
    }

    public NetworkDTO saveNetwork(Network network) {
        return networkRepository.save(NetworkParser.networkToNewEntity( network,
                                                                        getNetworksNewCountryTagsAndAreaTags(network),
                                                                        getExistingForTagEntities(network)));
    }

    private Set<CountryTagDTO> getNetworksNewCountryTagsAndAreaTags(Network network){
        return network.getCountryTags()
                .stream()
                .map(countryTag ->
                   NetworkParser.countryTagToNewEntity(countryTag, checkIfAreaTagsSetIsNotNull(countryTag)))
                .collect(Collectors.toSet());
    }

    private Set<AreaTagDTO> checkIfAreaTagsSetIsNotNull(CountryTag countryTag) {
        Set<AreaTagDTO> areaTagDTOs = null;
        if (countryTag.getAreaTags() != null) {
            areaTagDTOs = countryTag.getAreaTags()
                    .stream()
                    .map(areaTag ->
                            NetworkParser.areaTagToNewEntity(areaTag))
                    .collect(Collectors.toSet());
        }
        return areaTagDTOs;
    }

    public Set<ForTagDTO> getExistingForTagEntities(Network network){
        Set<ForTagDTO> set = null;
        if(network.getForTags() != null) {
            network.getForTags()
                    .forEach(tag ->
                            forTagRepository.findByName(tag.getName())
                                    .ifPresent(country -> set.add(country)));
        }
        return set;
    }


    public List<Network> getNetworks(NetworkParam param) {
        return networkRepository.findAll(getPageRequest(param))
                .getContent()
                .stream()
                .map(network ->
                         NetworkParser.entityToNetwork( network,
                                                        parseCountryTags(network.getCountryTags()),
                                                        parseForTags(network.getForTags())))
                .collect(Collectors.toList());

    }

    private Set<CountryTag> parseCountryTags(Set<CountryTagDTO> countryTagDTOs){
        return countryTagDTOs
                .stream()
                .map(country ->
                        NetworkParser.entityToExistingCountryTag(country, parseAreaTags(country.getAreaTagDTOs())))
                .collect(Collectors.toSet());
    }
    private List<CountryTag> parseCountryTags(List<CountryTagDTO> countryTagDTOs){
        return countryTagDTOs
                .stream()
                .map(country ->
                        NetworkParser.entityToExistingCountryTag(country, parseAreaTags(country.getAreaTagDTOs())))
                .collect(Collectors.toList());
    }

    private List<AreaTag> parseAreaTags(Set<AreaTagDTO> areaTagDTOs){
        return  areaTagDTOs
                .stream()
                .map(area ->
                    NetworkParser.entityToExsistingAreaTag(area))
                .collect(Collectors.toList());
    }

    private Set<ForTag> parseForTags(Set<ForTagDTO> forTagDTOs){
        return forTagDTOs.stream()
                .map(forTag ->
                        NetworkParser.entityToExsistingForTag(forTag))
                .collect(Collectors.toSet());
    }
    private List<ForTag> parseForTags(List<ForTagDTO> forTagDTOs){
        return forTagDTOs.stream()
                .map(forTag ->
                        NetworkParser.entityToExsistingForTag(forTag))
                .collect(Collectors.toList());
    }


    private PageRequest getPageRequest(NetworkParam pageParam){
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
    }

    public TagViewAlternative getTagAlternatives(){
        List<ForTagDTO> forTags = forTagRepository.findAll(sortByName);
        List<CountryTagAlternativeDTO> countryTagAlternatives = countryTagAlternativeRepository.findAll(sortByName);
        return new TagViewAlternative(parseCountryTagAlternativesWithAreaTagAlternatives(countryTagAlternatives), parseForTags(forTags));
    }

    private List<CountryTagAlternative> parseCountryTagAlternativesWithAreaTagAlternatives(List<CountryTagAlternativeDTO> countryTagAlternatives){
        return countryTagAlternatives
                .stream()
                .map(country ->
                     NetworkParser.entityToExistingCountryTagAlternative(country, getAreaTagAlternatives(country)))
                .collect(Collectors.toList());
    }

    private List<AreaTagAlternative> getAreaTagAlternatives(CountryTagAlternativeDTO country){
        return country.getAreaTagAlternativeDTOs()
                .stream()
                .map(city ->
                        NetworkParser.entityToExistingAreaTagAlternative(city))
                .collect(Collectors.toList());
    }

    public void connectAreaTagAlternativeToCountryTagAlternative(TagUpdateAlternative countryTagUpdate) {
        countryTagAlternativeRepository.findByName(countryTagUpdate.getCountryTag())
                .map(country -> {
                    country.getAreaTagAlternativeDTOs().add(new AreaTagAlternativeDTO(null, countryTagUpdate.getAreaTag()));
                    return countryTagAlternativeRepository.save(country);})
                .orElseThrow(BadRequestException::new);
    }

    public Set<AreaTagAlternative> getAreaTagAlternativesForCountryTagAlternative(String country) {
        return countryTagAlternativeRepository.findByName(country)
                .map(tag ->
                        tag.getAreaTagAlternativeDTOs()
                                .stream()
                                .map(area ->
                                        NetworkParser.entityToExistingAreaTagAlternative(area)).collect(Collectors.toSet()))
                .orElseThrow(BadRequestException::new);
    }

    public TagView getUsedTags() {
        List<CountryTagDTO> countryTagDTOs = countryTagRepository.findAll(sortByName)
                .stream()
                .filter(distinctByKey(p -> p.getName()))
                .collect(Collectors.toList());
        List<ForTagDTO> forTagDTOs = forTagRepository.findAll(sortByName);
        return new TagView(parseCountryTags(countryTagDTOs), parseForTags(forTagDTOs));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
