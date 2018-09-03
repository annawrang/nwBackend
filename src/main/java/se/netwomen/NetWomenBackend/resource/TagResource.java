package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Component
@Path("tags")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagResource {
    private final NetworkService networkService;

    public TagResource(NetworkService networkService) {
        this.networkService = networkService;
    }

    @POST
    @Path("/for")
    public Response createForTag(ForTag forTag){
        networkService.saveForTag(forTag);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/for")
    public Response getForTags(){
        return Response.ok(networkService.getForTags()).build();
    }

    @GET
    public Response getUsedTags() {
        return Response.ok(networkService.getUsedTags()).build();
    }

    @Path("{countryName}")
    @GET public Response findAreaTagsFromCountryName(@PathParam("countryName") String country){
        return Response.ok(networkService.findAreaTagsFromCountryName(country)).build();
    }
}
