package se.netwomen.NetWomenBackend.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.model.data.ProfileTest;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.service.ProfileService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import static javax.ws.rs.core.Response.Status.CREATED;

@Component
@Path("profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private final ProfileService service;
    @Context
    private UriInfo uriInfo;
    @Context
    private HttpHeaders requestHeaders;

    @Autowired
    public ProfileResource(ProfileService profileService) {
        this.service = profileService;
    }

   @POST
    public Response createProfile(ProfileDTO profileDTO) {
        profileDTO = service.createProfile(profileDTO);
        return Response.status(CREATED).build();
    }
/*Funkar ej än*/

  /*  @POST
    public Response createProfile2(Profile profile) {
        service.createProfile2(profile);
        return Response.status(Response.Status.CREATED).build();
    }
*/
    /*@POST
    @Path("{userNumber}")
    public Response createNewProfile(@QueryParam("userNumber") String userNumber, Profile profile) {
        profile = service.save(userNumber, profile);
        return Response.status(CREATED).build();
    }*/

    @GET
    @Path("{profileNumber}")
    public Response getProfileByProfileNumber(@PathParam("profileNumber") String profileNumber){
        return Response.ok(service.getProfileByProfileNumber(profileNumber)).build();
    }

    @GET
    public Response getAll() {
        return Response.ok(service.getAllProfiles()).build();
    }
    /*
    * TODO- PUT, DELETE METODER!!
    */
}
