package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.TagUpdateAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.CountryTagAlternative;
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
    @Path("/country")
    public Response createCountryTag(Set<CountryTagAlternative> countryTag){
        networkService.saveCountries(countryTag);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/area")
    public Response connectAreaTagAlternativeToCountryTagAlternative(TagUpdateAlternative countryTagUpdate){
        networkService.connectAreaTagAlternativeToCountryTagAlternative(countryTagUpdate);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/for")
    public Response createForTag(ForTag forTag){
        networkService.saveForTag(forTag);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/alternatives")
    public Response getAlternativeTags(){
        return Response.ok(networkService.getAlternativeTags()).build();
    }

    @GET
    @Path("/used")
    public Response getUsedTags() {
        return Response.ok(networkService.getUsedTags()).build();
    }

    @Path("{countryName}")
    @GET public Response findAreaTagAlternativesFromCountryName(@PathParam("countryName") String country){
        return Response.ok(networkService.findAreaTagAlternativesFromCountryName(country)).build();
    }
}
