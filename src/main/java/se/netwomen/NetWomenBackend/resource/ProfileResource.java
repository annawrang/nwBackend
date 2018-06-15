package se.netwomen.NetWomenBackend.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.service.ProfileService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("profiles")
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class ProfileResource {

    private final ProfileService service;
    @Context
    private UriInfo uriInfo;

    @Autowired
    public ProfileResource(ProfileService profileService) {
        this.service = profileService;
    }
    @POST
    public Response createNewProfile(Profile profile) {
        service.createProfile(profile);
        return Response.ok()
                .build();
    }
}
