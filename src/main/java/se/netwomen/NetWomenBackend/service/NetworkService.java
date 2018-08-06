package se.netwomen.NetWomenBackend.service;

import javassist.tools.web.BadHttpRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CityTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CountryTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.ForTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CityTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CountryTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.ForTagRepository;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;

import javax.ws.rs.BadRequestException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NetworkService {
    private final NetworkRepository networkRepository;
    private final CountryTagRepository countryTagRepository;
    private final CityTagRepository cityTagRepository;
    private final ForTagRepository forTagRepository;

    public NetworkService(NetworkRepository networkRepository, CountryTagRepository countryTagRepository, CityTagRepository cityTagRepository, ForTagRepository forTagRepository) {
        this.networkRepository = networkRepository;
        this.countryTagRepository = countryTagRepository;
        this.cityTagRepository = cityTagRepository;
        this.forTagRepository = forTagRepository;
    }

    public void saveCountry(Set<CountryTag> countryTag) {
       /* countryTagRepository.findByName(countryTag.getName())
                .ifPresent(s -> {throw new BadRequestException();});
        return countryTagRepository.save(NetworkParser.countryTagToNewEntity(countryTag));*/
       countryTag.forEach(country -> countryTagRepository.save(NetworkParser.countryTagToNewEntity(country)));
    }
/*
    public CityTagDTO saveCity(CityTag cityTag) {
        cityTagRepository.findByName(cityTag.getName())
                .ifPresent(s -> {throw new BadRequestException();});
        return cityTagRepository.save(NetworkParser.cityTagtoNewEntity(cityTag));
    }
*/
    public ForTagDTO saveFor(ForTag forTag) {
        forTagRepository.findByName(forTag.getName())
                .ifPresent(s -> {throw new BadRequestException();});
        return forTagRepository.save(NetworkParser.forTagtoNewEntity(forTag));
    }

    public NetworkDTO saveNetwork(Network network) {
        return networkRepository.save(NetworkParser.networkToNewEntity(
                network,
                getExistingCountryTagEntities(network),
                getExistingForTagEntities(network)));
    }

    public Set<CountryTagDTO> getExistingCountryTagEntities(Network network){
        Set<CountryTagDTO> set = new HashSet();
        network.getCountryTags()
                .forEach(tag ->
                        countryTagRepository.findByName(tag.getName())
                                .ifPresent(country -> set.add(country)));
        return set;
    }
    /*
    public Set<CityTagDTO> getExistingCityTagEntities(Network network){
        Set<CityTagDTO> set = new HashSet();
        network.getCountryTags()
                .forEach(country ->{
                        country.}
                                .ifPresent(country -> set.add(country)));
        return set;
    }
*/
    public Set<ForTagDTO> getExistingForTagEntities(Network network){
        Set<ForTagDTO> set = new HashSet();
        network.getForTags()
                .forEach(tag ->
                        forTagRepository.findByName(tag.getName())
                                .ifPresent(country -> set.add(country)));
        return set;
    }


    public List<Network> getNetworks(NetworkParam param) {
        return networkRepository.findAll(getPageRequest(param))
                .getContent()
                .stream()
                .map(network -> {
                        Set<CountryTag> countryTags = network.getCountryTags()
                                .stream()
                                .map(country ->
                                        NetworkParser.entityToExistingCountryTag(country, getCityTags(country)))
                                .collect(Collectors.toSet());
                        Set<ForTag> forTags = network.getForTags()
                                .stream()
                                .map(forTag ->
                                        NetworkParser.entityToExsistingForTag(forTag))
                                .collect(Collectors.toSet());
                        return NetworkParser.entityToNetwork(network, countryTags, forTags);
                }).collect(Collectors.toList());

    }


    private PageRequest getPageRequest(NetworkParam pageParam){
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
    }

/*
    public Network getNetwork(String networkNumber) {
        return networkRepository.findByNetworkNumber(networkNumber)
                .map(NetworkParser::networkDTOtoNetwork)
                .orElseThrow(BadRequestException::new);
    }

    public void updateNetWork(String networkNumber, NetworkUpdate networkUpdate) {
        networkRepository.findByNetworkNumber(networkNumber)
                .map(network ->
                        networkRepository.save(NetworkParser.networkUpdateToNetworkDto(network , networkUpdate)))
                .orElseThrow(BadRequestException::new);
    }

    */

    public TagView getTags(){
        return new TagView(getCountryTagsWithCityTags(), getForTags());
    }
    private List<CountryTag> getCountryTagsWithCityTags(){
        return countryTagRepository.findAll(new Sort(Sort.Direction.ASC, "name"))
                .stream()
                .map(country ->
                     NetworkParser.entityToExistingCountryTag(country, getCityTags(country)))
                .collect(Collectors.toList());
    }

    private List<CityTag> getCityTags(CountryTagDTO country){
        return country.getCityTagDTOs()
                .stream()
                .map(city ->
                        NetworkParser.entityToExistingCityTag(city)).collect(Collectors.toList());
    }

    private List<ForTag> getForTags(){
        return forTagRepository.findAll(new Sort(Sort.Direction.ASC, "name"))
                .stream()
                .map(NetworkParser::entityToExsistingForTag)
                .collect(Collectors.toList());
    }

    public void updateCountry(CountryTagUpdate countryTagUpdate) {
      /* Set<CityTagDTO> cityTagDTOs = countryTagRepository.findByName(countryTagUpdate.getCountryTag())
                .map(country -> cityTagRepository.findByCountryTagDTO(country))
                   // countryTagRepository.save(NetworkParser.countryTagtoNewEntity(countryTagUpdate.getCityTag(), country)))
                .orElseThrow(BadRequestException::new);
       cityTagDTOs.add( new CityTagDTO(null, countryTagUpdate.getCityTag()));
       countryTagRepository.*/
        countryTagRepository.findByName(countryTagUpdate.getCountryTag())
                .map(country -> {
                    country.getCityTagDTOs().add(new CityTagDTO(null, countryTagUpdate.getCityTag()));
                    return countryTagRepository.save(country);})
                .orElseThrow(BadRequestException::new);
    }

    public Set<CityTag> getCitiesForCountry(String country) {
        return countryTagRepository.findByName(country)
                .map(tag -> tag.getCityTagDTOs()
                        .stream()
                        .map(city ->
                        NetworkParser.entityToExistingCityTag(city)).collect(Collectors.toSet()))
                .orElseThrow(BadRequestException::new);
    }
}
