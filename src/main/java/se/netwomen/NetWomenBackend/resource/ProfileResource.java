package se.netwomen.NetWomenBackend.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.model.data.ProfileTest;
import se.netwomen.NetWomenBackend.model.data.User;
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
    public Response createNewProfile(@QueryParam("userNumber") String userNumber, Profile profile) {
        profile = service.saveUser(userNumber, profile);
        return Response.status(CREATED).build();
    }

    @GET
    public Response getAll() {
        return Response.ok(service.getAllProfiles()).build();
    }
    /*
   @POST
    public Response createNewPost(@QueryParam("userNumber") String userNumber, Post post) {
        postService.saveNewPost(userNumber, post);
        return Response.status(CREATED).build();
    }
    }*/
}
