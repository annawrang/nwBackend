package se.netwomen.NetWomenBackend.service.logic;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.network.NetworkFilter;
import se.netwomen.NetWomenBackend.model.data.network.NetworkForm;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.Location;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.ForTagRepository;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public final class NetworkLogic {
    private final ForTagRepository forTagRepository;
    private final NetworkRepository networkRepository;
    private static final String DEFAULT_IMG= "http://www.st-damien.com/uploads/8/0/4/0/80408348/person-1824147-1280_12_orig.png";

    public NetworkLogic(ForTagRepository forTagRepository, NetworkRepository networkRepository) {
        this.forTagRepository = forTagRepository;
        this.networkRepository = networkRepository;
    }

    public NetworkForm checkIfImgExists(NetworkForm network) {
        if(network.getPictureUrl().equals("")){
            network.setPictureUrl(DEFAULT_IMG);
        }
        return network;
    }

    public NetworkForm addLocationIfNetworkIsGlobal(NetworkForm network) {
        if(network.isGlobal()){
            network.getLocations().add(new Location("Global", null));
        }
        return network;
    }

    public boolean countryParamHasValue(NetworkParam param){
        return !(param.getCountry() == null || param.getCountry().equals("null"));
    }

    public boolean forTagParamHasValue(NetworkParam param){
        return !(param.getForTag() == null || param.getForTag().equals("null") ||  param.getForTag().equals(""));
    }

    public boolean areaParamHasValue(NetworkParam param){
        return !(param.getArea() == null|| param.getArea().equals("null"));
    }

    public void validateForTagDontExist(ForTag forTag) {
        forTagRepository.findByName(forTag.getName())
                .ifPresent(forTagDTO ->  new BadRequestException());
    }

    public List<NetworkFilter> filterAndConcatSearchReults(String searchText) {
        List<NetworkDTO> networkDTOS = networkRepository.findAll();
        return Stream.concat(
                Stream.concat(
                        filterByNames(networkDTOS, searchText).stream(),
                        filterByDescriptions(networkDTOS, searchText).stream()),
                filterByForTagName(searchText).stream())
                .collect(Collectors.toList());

    }

    private List<NetworkFilter> filterByForTagName(String searchText){
        return forTagRepository.findByNameStartingWithIgnoreCase(searchText)
                .stream()
                .map(forTag ->
                        NetworkParser.entityToNetworkFilter(null, forTag.getName(),
                                "Tag")).collect(Collectors.toList());
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
}
