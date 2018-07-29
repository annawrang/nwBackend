package se.netwomen.NetWomenBackend.resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Network;
import se.netwomen.NetWomenBackend.model.data.NetworkUpdate;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.NetworkService;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;
import se.netwomen.NetWomenBackend.service.exceptions.EmailNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
@Path("networks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NetworkResource {
    //private UriInfo uriInfo;

    private final NetworkService networkService;

    public NetworkResource(NetworkService networkService) {
        this.networkService = networkService;
    }

    @POST
    public Response createNetwork(Network network) {
        networkService.saveNetwork(network);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{networkNumber}")
    public Response getNetwork(@PathParam("networkNumber") String networkNumber) {
        return Response.ok(networkService.getNetwork(networkNumber)).build();
    }

    @GET
    public Response getNetworks(@BeanParam NetworkParam param){
        List<Network> networks = networkService.getNetworks(param);
        System.out.println("NÄTVÄRK STORLEK " + networks.size());
        return Response.ok(networks).build();
    }

    @PUT
    @Path(("{networkNumber}"))
    public Response updateNetwork(@PathParam("networkNumber") String networkNumber, NetworkUpdate networkUpdate){
        networkService.updateNetWork(networkNumber, networkUpdate);
        return Response.status(Response.Status.NO_CONTENT).build();

    }

    private String getUserNumberFromAuth(String auth){
        if(auth == null){
            throw new EmailNotFoundException("Usernumber not found.");
        }
        String userNumber = auth.split(";")[0];
        userNumber = userNumber.substring(userNumber.lastIndexOf(":") + 2).trim();
        System.out.println("userNumber==" + userNumber);
        return userNumber;
    }



}
