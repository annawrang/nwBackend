package se.netwomen.NetWomenBackend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Comment;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;
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

    @Autowired
    public UserResource(UserService userService) {
        this.service = userService;
    }


    // Authenticates user when they login, and sends a cookie (cookie is work in progress)
    @POST
    @Path("authenticate")
    public Response getUserByEmailAndPassword(@QueryParam("email") String email,
                                                       @QueryParam("password") String password){
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


    // Creates a Set-Cookie (not in use yet)
    private NewCookie createSetCookie(String email, String password) {
        String cookie = email + UUID.randomUUID();
        NewCookie newCookie = new NewCookie("name", cookie, "/", "", "no comment", 10000, false, false);
        System.out.println("\nCookien: " + newCookie.toString());
        service.setCookie(email, password, newCookie.toString());
        return newCookie;
    }
}
