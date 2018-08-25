package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class NetworkParser {

    public static NetworkDTO networkToNewEntity(Network network,Set<CountryTagDTO> countryTagDTOs, Set<ForTagDTO> forTags){
        return new NetworkDTO(null, network.getName(), network.getDescription(), network.getLink(), network.getPictureUrl(), countryTagDTOs, forTags, null, null, null, UUID.randomUUID().toString());
    }

    public static Network entityToNetwork(NetworkDTO networkDTO, Set<CountryTag> countryTags, Set<ForTag> forTags) {
        return new Network(networkDTO.getName(), networkDTO.getDescription(), networkDTO.getLink(), networkDTO.getPictureUrl(), countryTags, forTags, null, null, null, networkDTO.getNetworkNumber());
    }

    public static ForTagDTO forTagtoNewEntity(ForTag forTag) {
        return new ForTagDTO(null, forTag.getName().trim());
    }

    public static ForTag entityToExsistingForTag(ForTagDTO forTagDTO){
        return new ForTag(forTagDTO.getName());
    }

    public static CountryTagDTO countryTagToNewEntity(CountryTag countryTag, Set<AreaTagDTO> areaTagDTOs){
        return new CountryTagDTO(null, countryTag.getName(), areaTagDTOs);
    }

    public static CountryTag entityToExistingCountryTag(CountryTagDTO country, List<AreaTag> areaTags) {
        return new CountryTag(country.getName(), areaTags);
    }

    public static AreaTagDTO areaTagToNewEntity(AreaTag areaTag){
        return new AreaTagDTO(null, areaTag.getName());
    }

    public static AreaTag entityToExsistingAreaTag(AreaTagDTO areaTagDTO){
        return new AreaTag(areaTagDTO.getName());
    }

    public static Set<CountryTag> parseCountryTagEntities(Set<CountryTagDTO> countryTagDTOs){
        return countryTagDTOs
                .stream()
                .map(country ->
                        entityToExistingCountryTag(country, parseAreaTagEntities(country.getAreaTagDTOs())))
                .collect(Collectors.toSet());
    }

    public static List<CountryTag> parseCountryTagEntities(List<CountryTagDTO> countryTagDTOs){
        return countryTagDTOs
                .stream()
                .map(country ->
                        entityToExistingCountryTag(country, parseAreaTagEntities(country.getAreaTagDTOs())))
                .collect(Collectors.toList());
    }

    public static List<AreaTag> parseAreaTagEntities(Set<AreaTagDTO> areaTagDTOs){
        return  areaTagDTOs
                .stream()
                .map(area ->
                        entityToExsistingAreaTag(area))
                .collect(Collectors.toList());
    }

    public static Set<ForTag> parseForTagEntities(Set<ForTagDTO> forTagDTOs){
        return forTagDTOs.stream()
                .map(forTag ->
                        entityToExsistingForTag(forTag))
                .collect(Collectors.toSet());
    }

    public static List<ForTag> parseForTagEntities(List<ForTagDTO> forTagDTOs){
        return forTagDTOs.stream()
                .map(forTag ->
                        entityToExsistingForTag(forTag))
                .collect(Collectors.toList());
    }

    public static List<Network> parseNetworkEntities(List<NetworkDTO> networkDTOs){
        return networkDTOs.stream()
                .map(network ->
                        NetworkParser.entityToNetwork( network,
                                NetworkParser.parseCountryTagEntities(network.getCountryTags()),
                                NetworkParser.parseForTagEntities(network.getForTags())))
                .collect(Collectors.toList());
    }
}