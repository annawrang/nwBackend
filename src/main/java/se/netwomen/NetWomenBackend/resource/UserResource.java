package se.netwomen.NetWomenBackend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Optional;
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


    // Ska ej användas pga säkerhet, använd POST istället för att få användare när lösen och känslig data skickas
    @GET
    @Path("authenticate")
    public Response getUserByUsernameAndPassword(@QueryParam("userName") String userName,
                                                 @QueryParam("password") String password) {

        Optional<User> user = service.findByUserNameAndPassword(userName, password);
        if (user.isPresent()) {
            NewCookie setCookie = createSetCookie(userName, password);
            return Response.ok(user.get())
                    .cookie(setCookie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    // Ska ersätta GET ovan, pga säkerhet används POST när man skickar känslig data
//    @POST
//    @Path("authenticate")
//    public Response getUserByUserNameAndPasswordSafely(@QueryParam("userName") String userName,
//                                                       @QueryParam("password") String password){
//        Optional<User> user = service.findByUserNameAndPassword(userName, password);
//        if (user.isPresent()) {
//            return Response.ok(user.get())
//                    .build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

    // skapar en ny användare
    @POST
    public Response createNewUser(User user) {
        service.save(user);
        return Response.ok()
                .build();
    }

    // Här skapas Set-Cookie
    private NewCookie createSetCookie(String userName, String password) {
        UUID uuid = UUID.randomUUID();
        String cookie = userName + uuid;
        NewCookie newCookie = new NewCookie("name", cookie, "/", "", "comment", 100, false);
        service.setCookie(userName, password, newCookie);
        return newCookie;
    }
}
