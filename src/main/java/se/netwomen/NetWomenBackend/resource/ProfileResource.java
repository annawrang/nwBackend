package se.netwomen.NetWomenBackend.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileMine;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileOthers;
import se.netwomen.NetWomenBackend.service.ProfileService;
import se.netwomen.NetWomenBackend.service.exceptions.EmailNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("profile")
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private final ProfileService profileService;
    @Context
    private UriInfo uriInfo;

    @Autowired
    public ProfileResource(ProfileService profileService) {
        this.profileService = profileService;
    }


    @PUT
    @Path("{profileNumber}")
    public Response editProfile(@PathParam("profileNumber") String profileNumber, ProfileMine profileMine) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        if (userNumber == null || profileMine == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            if (userNumber.equals(profileMine.getUser().getUserNumber())) {
                profileService.editProfile(profileMine);
                return Response.ok().build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{profileNumber}")
    public Response getProfile(@PathParam("profileNumber") String profileNumber) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        if (userNumber == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else if (isSameUser(userNumber, profileNumber)) {
            ProfileMine profile = profileService.getMyProfile(userNumber, profileNumber);
            return Response.ok(profile).build();
        } else {
            ProfileOthers profile = profileService.getOthersProfile(profileNumber);
            return Response.ok(profile).build();
        }
    }

    private boolean isSameUser(String userNumber, String profileUserNumber) {
        if (userNumber.equals(profileUserNumber)) {
            return true;
        }
        return false;
    }

    // This is the method use to get the usernumber from the token, to always know which user is
    // sending the request
    private String getUserNumberFromAuth(String auth) {
        if (auth == null) {
            throw new EmailNotFoundException("Usernumber not found.");
        }
        String userNumber = auth.split(";")[0];
        userNumber = userNumber.substring(userNumber.lastIndexOf(":") + 2).trim();
        System.out.println("userNumber==" + userNumber);
        return userNumber;
    }
}
