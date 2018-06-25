package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

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

    public PostResource(PostService service) {
        this.postService = service;
    }

    @DELETE
    @Path("{postNumber}")
    public Response deletePost(@QueryParam("userNumber") String userNumber, Post post) {

        return null;
    }

    @POST
    public Response createNewPost(@QueryParam("userNumber") String userNumber, Post post) {
        postService.saveNewPost(userNumber, post);
        return Response.status(CREATED).build();
    }

    @PUT
    public Response editPost(@QueryParam("userNumber") String userNumber, Post post) {
        postService.editPost(userNumber, post);
        return Response.status(CREATED).build();
    }

    // Paging, vilken sida + antal per sida (default 10 per sida)
    @GET
    public Response getPostsTEMP(@BeanParam PostParam param) {
        List<PostComplete> posts = postService.getPostsAndLikesComments(param);
        return Response.ok(posts).build();
    }

    // Checks userNumber (goes is as QueryParam) instead of cookie for now
    @POST
    @Path("{postNumber}/likes")
    public Response likePost(@PathParam("postNumber") String postNumber,
                             @QueryParam("userNumber") String userNumber) {
        if (userNumber != null) {
            postService.saveNewPostLike(postNumber, userNumber);
            return Response.ok().build();
        }
        return Response.status(UNAUTHORIZED).build();
    }

}
