package se.netwomen.NetWomenBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import se.netwomen.NetWomenBackend.model.data.network.Network;

import se.netwomen.NetWomenBackend.controller.param.NetworkParam;
import se.netwomen.NetWomenBackend.model.data.network.NetworkForm;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping(value = "/networks")
public class NetworkResource {

    private final NetworkService networkService;

    public NetworkResource(NetworkService networkService) {
        this.networkService = networkService;
    }

    @PostMapping
    public ResponseEntity createNetwork(@RequestBody NetworkForm network) {
        System.out.println(network.getPictureUrl());
        networkService.saveNetworkForm(network);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value="{networkNumber}")
    public ResponseEntity getNetwork(@PathVariable("networkNumber") String networkNumber){
        return ResponseEntity.ok(networkService.getNetwork(networkNumber));
    }

    @PutMapping(value="{networkNumber}")
    public ResponseEntity updateNetwork(@PathVariable("networkNumber") String networkNumber, @RequestBody NetworkForm networkForm){
        networkService.updateNetwork(networkNumber, networkForm);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value="users/{userNumber}")
    public ResponseEntity getNetworks(@PathVariable("userNumber") String userNumber,
                                      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                      @RequestParam(name = "size", defaultValue = "18", required = false) int size,
                                      @RequestParam(name = "country", required = false) String country,
                                      @RequestParam(name = "area", required = false) String area,
                                      @RequestParam(name = "fortag",  required = false) String fortag){
        System.out.println("halla" + userNumber);
        return ResponseEntity.ok(networkService.getAllNetworks(userNumber, page, size, country, area, fortag));
    }

    @GetMapping(value="search")
    public ResponseEntity getNetworksFilterResults( @RequestParam(name = "search", required = false) String search) {
        return ResponseEntity.ok(networkService.getNetworksFilterSearchAutoSuggest(search));
    }

    //adds network to user
    @PostMapping(value = "current-user/{userNumber}")
    public ResponseEntity addNetworkToUser(@PathVariable("userNumber") String userNumber, @RequestBody Network network){
        networkService.addNetworkToUser(userNumber, network);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value="current-user/{userNumber}")
    public ResponseEntity findMyNetworksForUser( @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                 @RequestParam(name = "size", defaultValue = "18", required = false) int size,
                                                 @PathVariable("userNumber") String userNumber){
        return ResponseEntity.ok(networkService.findMyNetworksForUser(userNumber, page, size));
    }

}
