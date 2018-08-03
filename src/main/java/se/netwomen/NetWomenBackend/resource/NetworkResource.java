package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.network.NetworkTest;
import se.netwomen.NetWomenBackend.model.data.network.NetworkUpdate;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    /*
    @POST
    public Response createNetwork(Network network) {
        networkService.saveNetwork(network);
        return Response.status(Response.Status.CREATED).build();
    }
*/
    @POST
    public Response createNetwork(NetworkTest network) {
        System.out.println(network.getName() + "   " + network.getDescription() + "    " + network.getLink() + "  " + network.getPictureUrl());
        network.getCountryTags().forEach(tag -> System.out.println(tag.getName()));
        network.getCityTags().forEach(tag -> System.out.println(tag.getName()));
        return Response.status(Response.Status.CREATED).build();
    }
    /*
    @GET
    @Path("{networkNumber}")
    public Response getNetwork(@PathParam("networkNumber") String networkNumber) {
        return Response.ok(networkService.getNetwork(networkNumber)).build();
    }

    @GET
    public Response getNetworks(@BeanParam NetworkParam param){
        return Response.ok(networkService.getNetworks(param)).build();
    }


    @PUT
    @Path(("{networkNumber}"))
    public Response updateNetwork(@PathParam("networkNumber") String networkNumber, NetworkUpdate networkUpdate){
        networkService.updateNetWork(networkNumber, networkUpdate);
        return Response.status(Response.Status.NO_CONTENT).build();

    }
*/
    @DELETE
    @Path(("{networkNumber}"))
    public Response deleteNetwork(@PathParam("networkNumber") String networkNumber){
        networkService.deleteNetwork(networkNumber);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
