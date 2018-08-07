package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
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
    public Response createCityTagToCountryTag(AlternativeTagUpdate countryTagUpdate){
        System.out.println(countryTagUpdate.getCountryTag() + "    " + countryTagUpdate.getAreaTag());
        networkService.updateCityForCountry(countryTagUpdate);
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
        TagView tagView = networkService.getTags();
        tagView.getCountryTags().forEach(country -> country.getCityTags().forEach( cityTag -> System.out.println(cityTag.getName())));
        return Response.ok(networkService.getTags()).build();
    }
    @Path("{countryName}")
    @GET public Response getCitiesForCountry(@PathParam("countryName") String country){
        return Response.ok(networkService.getCitiesForCountry(country)).build();
    }
}
