package se.netwomen.NetWomenBackend.service;

import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.network.tag.CityTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.CountryTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CityTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CountryTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.ForTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CityTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.CountryTagRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.ForTagRepository;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

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
    /*
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

    private PageRequest getPageRequest(NetworkParam pageParam){
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
    }
    */

    public void deleteNetwork(String networkNumber) {
        Optional<NetworkDTO> networkDTO = networkRepository.findByNetworkNumber(networkNumber);
        networkDTO.ifPresent(network -> networkRepository.delete(network));
        networkDTO.orElseThrow(BadRequestException::new);

    }

    public CountryTagDTO createCountryTag(CountryTag countryTag) {
        //TODO söka efter namnet så de inte blir dubbletter
        return countryTagRepository.save(NetworkParser.countryTagDTOToNewCountryTag(countryTag));
    }
    public CityTagDTO createCityTag(CityTag cityTag) {
        //TODO söka efter namnet så de inte blir dubbletter
        return cityTagRepository.save(NetworkParser.cityTagDTOtoNewCityTag(cityTag));
    }
    public ForTagDTO createForTag(ForTag forTag) {
        //TODO söka efter namnet så de inte blir dubbletter
        return forTagRepository.save(NetworkParser.forTagDTOtoNewForTag(forTag));
    }
}
