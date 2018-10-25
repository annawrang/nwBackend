package se.netwomen.NetWomenBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.netwomen.NetWomenBackend.model.data.network.Network;

import se.netwomen.NetWomenBackend.controller.param.NetworkParam;
import se.netwomen.NetWomenBackend.model.data.network.NetworkForm;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;



@RestController
@RequestMapping(value = "/networks")
public class NetworkResource {

    private final NetworkService networkService;

    public NetworkResource(NetworkService networkService) {
        this.networkService = networkService;
    }

    @PostMapping
    public ResponseEntity createNetwork(NetworkForm network) {
        System.out.println(network.getPictureUrl());
        networkService.saveNetworkForm(network);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value="{networkNumber}")
    public ResponseEntity getNetwork(@PathParam("networkNumber") String networkNumber){
        return ResponseEntity.ok(networkService.getNetwork(networkNumber));
    }

    @GetMapping(value="users/{userNumber]")
    public ResponseEntity getNetworks(@BeanParam NetworkParam param, @PathParam("userNumber") String userNumber){
        return ResponseEntity.ok(networkService.getAllNetworks(param, userNumber));
    }

    @GetMapping(value="search")
    public ResponseEntity getNetworksFilterResults(@BeanParam NetworkParam param) {
        return ResponseEntity.ok(networkService.getNetworksFilterSearchAutoSuggest(param));
    }

    //adds network to user
    @PostMapping(value = "current-user/{userNumber}\"")
    public ResponseEntity addNetworkToUser(@PathParam("userNumber") String userNumber, Network network){
        networkService.addNetworkToUser(userNumber, network);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value="current-user/{userNumber}")
    public ResponseEntity findMyNetworksForUser(@BeanParam NetworkParam param, @PathParam("userNumber") String userNumber){
        return ResponseEntity.ok(networkService.findMyNetworksForUser(userNumber, param));
    }



}
