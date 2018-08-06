package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.tag.CityTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.CountryTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.CountryTagUpdate;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CityTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CountryTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.ForTagDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class NetworkParser {

    public static NetworkDTO networkToNewEntity(Network network, Set<CountryTagDTO> countryTags, Set<ForTagDTO> forTags){
        return new NetworkDTO(null, network.getName(), network.getDescription(), network.getLink(), network.getPictureUrl(),countryTags, forTags, null, null, null, UUID.randomUUID().toString());
    }

    public static Network entityToNetwork(NetworkDTO networkDTO, Set<CountryTag> countryTags, Set<ForTag> forTags) {
        return new Network(networkDTO.getName(), networkDTO.getDescription(), networkDTO.getLink(), networkDTO.getPictureUrl(), countryTags, forTags, null, null, null, networkDTO.getNetworkNumber());
    }
    /*
    public static NetworkDTO networkToNetworkDTO(NetworkTest network) {
        return new NetworkDTO(null, network.getName(), network.getDescription(), network.getLink(), network.getPictureUrl(), network.getCountryTags(), network.getCityTags(), network.getForTags(), network.getOfferTags(), network.getTypeTags(), network.getOtherTags(), UUID.randomUUID().toString());
    }
  */


    public static CountryTagDTO countryTagToNewEntity(CountryTag countryTag){ // ny tag
        return new CountryTagDTO(null, countryTag.getName().trim(), null);
    }

    public static CountryTag entityToExistingCountryTag(CountryTagDTO countryTagDTO, List<CityTag> cityTags){
        return new CountryTag(countryTagDTO.getName(), cityTags);
    }

    public static CityTagDTO cityTagtoNewEntity(String cityTag) {
        return new CityTagDTO(null, cityTag.trim() );
    }

    public static CityTag entityToExistingCityTag(CityTagDTO cityTagDTO){
        return new CityTag(cityTagDTO.getName());
    }

    public static ForTagDTO forTagtoNewEntity(ForTag forTag) {
        return new ForTagDTO(null, forTag.getName().trim());
    }

    public static ForTag entityToExsistingForTag(ForTagDTO forTagDTO){
        return new ForTag(forTagDTO.getName());
    }


/*
    public static NetworkDTO networkUpdateToNetworkDto(NetworkDTO network, NetworkUpdate networkUpdate){
        return new NetworkDTO(network.getId(), networkUpdate.getName(), networkUpdate.getDescription(), networkUpdate.getLink(), networkUpdate.getPictureUrl(), networkUpdate.getCity(), networkUpdate.getCountry(), network.getNetworkNumber());
    }
    */
}