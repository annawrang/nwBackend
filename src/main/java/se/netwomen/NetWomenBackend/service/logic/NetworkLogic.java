package se.netwomen.NetWomenBackend.service.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.NetworkFilter;
import se.netwomen.NetWomenBackend.model.data.network.NetworkForm;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.Location;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CountryTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.ForTagDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag.ForTagRepository;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;

import javax.ws.rs.BadRequestException;
import java.util.*;
import java.util.stream.Collectors;

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


    //getNetworks filter
    public boolean countryParamHasValue(NetworkParam param){
        return !(param.getCountry() == null || param.getCountry().equals("null"));
    }

    public boolean forTagParamHasValue(NetworkParam param){
        return !(param.getForTag() == null || param.getForTag().equals("null") ||  param.getForTag().equals(""));
    }

    public boolean areaParamHasValue(NetworkParam param){
        return !(param.getArea() == null|| param.getArea().equals("null"));
    }

    public void validateForTagDontAlreadyExist(ForTag forTag) {
        forTagRepository.findByName(forTag.getName())
                .ifPresent(forTagDTO ->  new BadRequestException());
    }

    public List<Network> findNetworksByForTagNames(NetworkParam param){
        Page<NetworkDTO> networkDTOPage = networkRepository.findByForTagsName(param.getForTag(), getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
    }

    public Set<Network> changeNetworkToTrueIfUserHasItInMyNetworks(List<Network> networks, String userNumber) {
        List<Network> myNetworksForUser = findMyNetworksForUser(userNumber);
        Set<Network> customSet = new HashSet<>();
        for (Network network : networks) {
            if(myNetworksForUser.contains(network)){
                network.setMyNetwork(true); //if duplicate
                customSet.add(network);
            } else {
                network.setMyNetwork(false); // if not duplicate
                customSet.add(network);
            }
        }
        return customSet;
    }

    public List<Network> findMyNetworksForUser(String usernumber) {
        List<NetworkDTO> myNetworkDTOs = networkRepository.findByUsersUserNumber(usernumber);
        return NetworkParser.parseNetworkEntities(myNetworkDTOs);
    }

    public List<Network> findNetworksByForTagNamesAndCountryName(NetworkParam param){
        Page<NetworkDTO> networkDTOPage = networkRepository.findDistinctByForTagsNameInAndCountryTagsName(param.getForTag(), param.getCountry(), getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
    }

    public List<Network> findNetworksByAreaTagName(List<Network> networks, NetworkParam param) {
        return networks
                .stream()
                .filter(network -> network.getCountryTags().stream()
                        .flatMap(countryTag ->  countryTag.getAreaTags().stream())
                        .anyMatch(areaTag -> areaTag.getName().equalsIgnoreCase(param.getArea())))
                .collect(Collectors.toList());
    }

    public List<Network> findAllNetworks(NetworkParam param) {
        Page<NetworkDTO> networkDTOPage = networkRepository.findAll(getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
    }

    public List<Network> findNetworksByCountryTagName(NetworkParam param) {
        Page<NetworkDTO> networkDTOPage = networkRepository.findByCountryTagsName(param.getCountry(), getPageRequest(param));
        return NetworkParser.parseNetworkEntities(networkDTOPage.getContent());
    }

    public List<NetworkFilter> filterAndConcatSearchAutosuggestReults(String searchText) {
        List<NetworkDTO> networkDTOS = networkRepository.findAll();
        List<NetworkFilter> searchResultList = new ArrayList<>(filterByNames(networkDTOS, searchText));
        searchResultList.addAll(filterByDescriptions(networkDTOS, searchText));
        searchResultList.addAll(filterByForTagName(searchText));
        return searchResultList;
    }

    private List<NetworkFilter> filterByForTagName(String searchText){
        return forTagRepository.findByNameStartingWithIgnoreCase(searchText)
                .stream()
                .map(forTag ->
                        NetworkParser.entityToNetworkFilter(null, forTag.getName(),
                                "Tag"))
                .collect(Collectors.toList());
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


    // Create Network Methods
    public Set<CountryTagDTO> parseCountryTagsIfNotNull(NetworkForm network){
        return Optional.ofNullable(network.getLocations())
                .orElseGet(Collections::emptySet)
                .stream()
                .map(NetworkParser::locationToEntity)
                .collect(Collectors.toSet());
    }

    public Set<ForTagDTO> parseForTagsIfNotNull(NetworkForm network){
        Set<ForTagDTO> set = new HashSet<>();
        Optional.ofNullable(network.getForTags())
                .orElseGet(Collections::emptySet)
                .stream()
                .map(tag ->
                        forTagRepository.findByName(tag.getName())
                                .map(forTag -> set.add(forTag)));
        return set;
    }

    private PageRequest getPageRequest(NetworkParam pageParam){
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
    }

}
