package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.network.tag.CityTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.CountryTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("networks")
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
        networkService.createCountryTag(countryTag);
        return Response.status(Response.Status.OK).build();
    }
    @POST
    @Path("/city")
    public Response createCityTag(CityTag cityTag){
        networkService.createCityTag(cityTag);
        return Response.status(Response.Status.OK).build();
    }
    @POST
    @Path("/for")
    public Response createForTag(ForTag forTag){
        networkService.createForTag(forTag);
        return Response.status(Response.Status.OK).build();
    }
}
