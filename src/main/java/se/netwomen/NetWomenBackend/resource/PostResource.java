package se.netwomen.NetWomenBackend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.resource.security.JwtTokenProvider;
import se.netwomen.NetWomenBackend.service.PostService;
import se.netwomen.NetWomenBackend.service.exceptions.EmailNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

@Component
@Path("posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    @Context
    private UriInfo uriInfo;
    @Context
    private HttpHeaders requestHeaders;
    private final PostService postService;

    @Autowired
    public PostResource(PostService service, JwtTokenProvider jwtTokenProvider) {
        this.postService = service;
    }

    @DELETE
    @Path("{postNumber}")
    public Response deletePost(@PathParam("postNumber")String postNumber) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        if (postService.deletePost(postNumber, userNumber)){
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @POST
    public Response createNewPost(Post post) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        postService.saveNewPost(userNumber, post);
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("{postNumber}")
    public Response editPost(@PathParam("postNumber")String postNumber, String newText) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        if(postService.editPost(postNumber, userNumber, newText)){
            return Response.status(CREATED).build();
        }
        return Response.status(BAD_REQUEST).build();
    }

    // Paging, vilken sida + antal per sida (default 10 per sida)
    @GET
    public Response getPostsPaged(@BeanParam PostParam param) {
        List<PostComplete> posts = postService.getPostsAndLikesComments(param);
        return Response.ok(posts).build();
    }


    @POST
    @Path("{postNumber}/likes")
    public Response createNewPostLike(@PathParam("postNumber") String postNumber) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        if (userNumber != null) {
            postService.saveNewPostLike(postNumber, userNumber);
            return Response.ok().build();
        }
        return Response.status(UNAUTHORIZED).build();
    }

    // This is the method use to get the usernumber from the token, to always know which user is
    // sending the request
    private String getUserNumberFromAuth(String auth){
        if(auth == null){
            throw new EmailNotFoundException("Usernumber not found.");
        }
        String userNumber = auth.split(";")[0];
        userNumber = userNumber.substring(userNumber.lastIndexOf(":") + 2).trim();
        System.out.println("userNumber==" + userNumber);
        return userNumber;
    }

}
