package se.netwomen.NetWomenBackend.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Network;
import se.netwomen.NetWomenBackend.model.data.NetworkUpdate;
import se.netwomen.NetWomenBackend.repository.DTO.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NetworkService {
    private final NetworkRepository networkRepository;

    public NetworkService(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
    }
    public NetworkDTO saveNetwork(Network network) {
        return networkRepository.save(NetworkParser.networkToNetworkDTO(network));
    }

    public Network getNetwork(String networkNumber) {
        return networkRepository.findByNetworkNumber(networkNumber)
                .map(NetworkParser::networkDTOtoNetwork)
                .orElseThrow(BadRequestException::new);
    }

    public List<Network> getNetworks(NetworkParam param) {
       return networkRepository.findAll(getPageRequest(param))
                .getContent()
                .stream()
                .map(NetworkParser::networkDTOtoNetwork)
                .collect(Collectors.toList());
    }

    public void updateNetWork(String networkNumber, NetworkUpdate networkUpdate) {
        networkRepository.findByNetworkNumber(networkNumber)
                .map(network ->
                        networkRepository.save(NetworkParser.networkUpdateToNetworkDto(network , networkUpdate)))
                .orElseThrow(BadRequestException::new);
    }

    protected PageRequest getPageRequest(NetworkParam pageParam){
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
    }

    //Validation vilkor
}
