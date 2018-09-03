package se.netwomen.NetWomenBackend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
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
    private final ProfileService profileService;

    @Autowired
    public UserResource(UserService userService, ProfileService profileService) {
        this.service = userService;
        this.profileService = profileService;
    }


    // Authenticates user when they login, and sends a JwtToken
    @POST
    @Path("authenticate")
    public Response signIn(@QueryParam("email") String email,
                           @QueryParam("password") String password) {
        String jwtToken = this.service.signIn(email, password);
        User user = service.findByEmailAndPassword(email, password);
        if (user != null) {
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
        if (service.saveNewUser(user)) {
            return Response.status(Response.Status.CREATED)
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //adds network to user
    @POST
    @Path("{userNumber}/networks")
    public Response addNetworkToUser(@PathParam("userNumber") String userNumber, Network network){
        service.addNetworkToUser(userNumber, network);
        return Response.noContent().build();
    }

    @GET
    @Path("{userNumber}/networks")
    public Response findMyNetworksForUser(@BeanParam NetworkParam param, @PathParam("userNumber") String userNumber){
       return Response.ok(service.findMyNetworksForUser(userNumber, param)).build();
    }




}
