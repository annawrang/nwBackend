package se.netwomen.NetWomenBackend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Long id) {
        Optional<User> user = service.findById(id);
        if (user.isPresent()) {
            return Response.ok(user.get()).header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Ska ej användas pga säkerhet, använd POST istället för att få användare när lösen och känslig data skickas
//    @GET
//    public Response getUserByUsernameAndPassword(@QueryParam("userName") String userName,
//                                                 @QueryParam("password") String password) {
//        Optional<User> user = service.findByUserNameAndPassword(userName, password);
//        if (user.isPresent()) {
//            return Response.ok(user.get())
//                    .build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

    // Ska ersätta GET ovan, pga säkerhet används POST när man skickar känslig data
    @POST
    public Response getUserByUserNameAndPasswordSafely(@QueryParam("userName") String userName,
                                                       @QueryParam("password") String password){
        Optional<User> user = service.findByUserNameAndPassword(userName, password);
        if (user.isPresent()) {
            return Response.ok(user.get())
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // den heter det sålänge, för att kunna skiljas från metoden ovan
    @POST
    @Path("create")
    public Response createNewUser(User user) {
        service.save(user);
        return Response.ok()
                .build();
    }
}
