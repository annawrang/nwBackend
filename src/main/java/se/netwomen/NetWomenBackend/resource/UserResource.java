package se.netwomen.NetWomenBackend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.service.ProfileService;
import se.netwomen.NetWomenBackend.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.UUID;

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


    // Authenticates user when they login, and sends a cookie (cookie is work in progress)
    @POST
    @Path("authenticate")
    public Response getUserByEmailAndPassword(@QueryParam("email") String email,
                                              @QueryParam("password") String password) {
        User user = service.findByEmailAndPassword(email, password);
        if (user != null) {
            NewCookie cookie = createSetCookie(email, password);
            return Response.ok()
                    .cookie(new NewCookie("namn", "annaCookie"))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Creates a new user
    @POST
    public Response createNewUser(User user) {
        service.save(user);
        return Response.status(Response.Status.CREATED)
                .build();
    }

    /*Claudia- ska senare läggas till relevant fullnamn för att logga in på profil?*/
    @GET
    @Path("{firstName}/profile")
    public Response getUsersProfileByName(@PathParam("firstName") String firstName) {
        return Response.ok(profileService.findProfileByUserName(firstName)).build();
    }

    // Här skapas Set-Cookie
    private NewCookie createSetCookie(String userName, String password) {
        String cookie = userName + UUID.randomUUID();
        service.setCookie(userName, password, cookie);
        NewCookie newCookie = new NewCookie("name", cookie);
        return newCookie;
    }
}
