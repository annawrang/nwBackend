package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.NetworkTest;
import se.netwomen.NetWomenBackend.model.data.network.NetworkUpdate;
import se.netwomen.NetWomenBackend.model.data.network.tag.CityTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.CountryTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CityTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CountryTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.ForTagDTO;

import java.util.UUID;

public final class NetworkParser {
/*
    public static Network networkDTOtoNetwork(NetworkDTO networkDTO) {
        return new Network(networkDTO.getName(), networkDTO.getDescription(), networkDTO.getLink(), networkDTO.getPictureUrl(), networkDTO.getCity(), networkDTO.getCountry(), networkDTO.getNetworkNumber());
    }
    public static NetworkDTO networkToNetworkDTO(NetworkTest network) {
        return new NetworkDTO(null, network.getName(), network.getDescription(), network.getLink(), network.getPictureUrl(), network.getCountryTags(), network.getCityTags(), network.getForTags(), network.getOfferTags(), network.getTypeTags(), network.getOtherTags(), UUID.randomUUID().toString());
    }
  */


    public static CountryTagDTO countryTagDTOToNewCountryTag(CountryTag countryTag){ // ny tag
        return new CountryTagDTO(null, countryTag.getName());
    }

    public static CityTagDTO cityTagDTOtoNewCityTag(CityTag cityTag) {
        return new CityTagDTO(null, cityTag.getName());
    }

    public static ForTagDTO forTagDTOtoNewForTag(ForTag forTag) {
        return new ForTagDTO(null, forTag.getName());
    }
/*
    public static NetworkDTO networkUpdateToNetworkDto(NetworkDTO network, NetworkUpdate networkUpdate){
        return new NetworkDTO(network.getId(), networkUpdate.getName(), networkUpdate.getDescription(), networkUpdate.getLink(), networkUpdate.getPictureUrl(), networkUpdate.getCity(), networkUpdate.getCountry(), network.getNetworkNumber());
    }
    */
}