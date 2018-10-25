package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.model.data.network.NetworkForm;
import se.netwomen.NetWomenBackend.model.data.network.tag.AreaTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.CountryTag;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Path("networks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NetworkResource {

    private final NetworkService networkService;

    public NetworkResource(NetworkService networkService) {
        this.networkService = networkService;
    }

    @POST
    public Response createNetwork(NetworkForm network) {
        networkService.saveNetworkForm(network);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{networkNumber}")
    public Response getNetwork(@PathParam("networkNumber") String networkNumber){
        return Response.ok(networkService.getNetwork(networkNumber)).build();
    }
    @GET
    @Path("users/{userNumber}")
    public Response getNetworks(@BeanParam NetworkParam param, @PathParam("userNumber") String userNumber){
        return Response.ok(networkService.getAllNetworks(param, userNumber)).build();
    }

    @GET
    @Path("/search")
    public Response getNetworksFilterResults(@BeanParam NetworkParam param){
        return Response.ok(networkService.getNetworksFilterSearchAutoSuggest(param)).build();
    }

}
