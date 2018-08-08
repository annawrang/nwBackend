package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.AreaTagAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.CountryTagAlternative;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class NetworkParser {

    public static NetworkDTO networkToNewEntity(Network network,Set<CountryTagDTO> countryTagDTOs, Set<ForTagDTO> forTags){
        return new NetworkDTO(null, network.getName(), network.getDescription(), network.getLink(), network.getPictureUrl(), countryTagDTOs, forTags, null, null, null, UUID.randomUUID().toString());
    }

    public static Network entityToNetwork(NetworkDTO networkDTO, Set<CountryTag> countryTags, Set<ForTag> forTags) {
        return new Network(networkDTO.getName(), networkDTO.getDescription(), networkDTO.getLink(), networkDTO.getPictureUrl(), countryTags, forTags, null, null, null, networkDTO.getNetworkNumber());
    }
    /*
    public static NetworkDTO networkToNetworkDTO(NetworkTest network) {
        return new NetworkDTO(null, network.getName(), network.getDescription(), network.getLink(), network.getPictureUrl(), network.getCountryTags(), network.getCityTags(), network.getForTags(), network.getOfferTags(), network.getTypeTags(), network.getOtherTags(), UUID.randomUUID().toString());
    }
  */


    public static CountryTagAlternativeDTO countryTagAlternativeToNewEntity(CountryTagAlternative countryTag){ // ny tag
        return new CountryTagAlternativeDTO(null, countryTag.getName().trim(), null);
    }

    public static CountryTagAlternative entityToExistingCountryTagAlternative(CountryTagAlternativeDTO countryTagDTO, List<AreaTagAlternative> areaTagAlternatives){
        return new CountryTagAlternative(countryTagDTO.getName(), areaTagAlternatives);
    }

    public static AreaTag entityToExsistingAreaTag(AreaTagDTO areaTagDTO){
        return new AreaTag(areaTagDTO.getName());
    }
/*
    public static CityTagDTO cityTagtoNewEntity(String cityTag) {
        return new CityTagDTO(null, cityTag.trim() );
    }
    */

    public static AreaTagAlternative entityToExistingAreaTagAlternative(AreaTagAlternativeDTO areaTagAlternativeDTO){
        return new AreaTagAlternative (areaTagAlternativeDTO.getName());
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
    public static AreaTagDTO areaTagToNewEntity(AreaTag areaTag){
        return new AreaTagDTO(null, areaTag.getName());
    }

    public static CountryTag entityToExistingCountryTag(CountryTagDTO country, List<AreaTag> areaTags) {
        return new CountryTag(country.getName(), areaTags);
    }


/*
    public static NetworkDTO networkUpdateToNetworkDto(NetworkDTO network, NetworkUpdate networkUpdate){
        return new NetworkDTO(network.getId(), networkUpdate.getName(), networkUpdate.getDescription(), networkUpdate.getLink(), networkUpdate.getPictureUrl(), networkUpdate.getCity(), networkUpdate.getCountry(), network.getNetworkNumber());
    }
    */
}