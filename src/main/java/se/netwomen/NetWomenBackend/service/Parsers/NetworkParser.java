package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Network;
import se.netwomen.NetWomenBackend.model.data.NetworkUpdate;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import java.util.UUID;

public final class NetworkParser {

    public static Network networkDTOtoNetwork(NetworkDTO networkDTO) {
        return new Network(networkDTO.getName(), networkDTO.getDescription(), networkDTO.getLink(), networkDTO.getPictureUrl(), networkDTO.getCity(), networkDTO.getCountry(), networkDTO.getNetworkNumber());
    }

    public static NetworkDTO networkToNetworkDTO(Network network) {
        return new NetworkDTO(null, network.getName(), network.getDescription(), network.getLink(), network.getPictureUrl(), network.getCity(), network.getCountry(), UUID.randomUUID().toString());
    }

    public static NetworkDTO networkUpdateToNetworkDto(NetworkDTO network, NetworkUpdate networkUpdate){
        return new NetworkDTO(network.getId(), networkUpdate.getName(), networkUpdate.getDescription(), networkUpdate.getLink(), networkUpdate.getPictureUrl(), networkUpdate.getCity(), networkUpdate.getCountry(), network.getNetworkNumber());
    }
}
