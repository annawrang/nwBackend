package se.netwomen.NetWomenBackend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.service.ProfileService;
import se.netwomen.NetWomenBackend.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService service;
    private final ProfileService profileService;/*CLAUDIA*/

    @Autowired
    public UserResource(UserService userService, ProfileService profileService) {
        this.service = userService;
        this.profileService = profileService; /*CLAUDIA*/
    }


    // Authenticates user when they login, and sends a JwtToken
    @POST
    @Path("authenticate")
    public Response signIn(@QueryParam("email") String email,
                           @QueryParam("password") String password) {
        String jwtToken = this.service.signIn(email, password);
        User user = service.findByEmailAndPassword(email, password);
        if (user != null) {
            System.out.println("\nAnvändaren finns");
            return Response.ok()
                    .header("Auth-Token", jwtToken)
                    .header("Usernumber", user.getUserNumber())
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Creates a new user
    @POST
    @Path("signup")
    public Response createNewUser(User user) {
        service.save(user);
        return Response.status(Response.Status.CREATED)
                .build();
    }

    /*CLAUDIAS- Hämta profil baserat på förstanamn*/
    @GET
    @Path("{id}/profile")
    public Response getProfilePageForUser(@PathParam("id") Long id) {
        return Response.ok(profileService.findByUserId(id)).build();
    }

}
