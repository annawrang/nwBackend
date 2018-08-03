package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.network.tag.CityTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.CountryTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response createCountryTag(CountryTag countryTag){
        networkService.saveCountry(countryTag);
        return Response.status(Response.Status.OK).build();
    }
    @POST
    @Path("/city")
    public Response createCityTag(CityTag cityTag){
        networkService.saveCity(cityTag);
        return Response.status(Response.Status.OK).build();
    }
    @POST
    @Path("/for")
    public Response createForTag(ForTag forTag){
        networkService.saveFor(forTag);
        return Response.status(Response.Status.OK).build();
    }
    @GET
    public Response getTags(){
        return Response.ok(networkService.getTags()).build();
    }
}
