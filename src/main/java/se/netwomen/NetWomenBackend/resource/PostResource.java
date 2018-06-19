package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
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

    public PostResource(PostService service){
        this.postService = service;
    }

    @DELETE
    @Path("{postNumber}")
    public Response deletePost(Post post){
        return null;
    }

    @POST
    public Response createNewPost(Post post){
        // kolla att cookie-användare och inskickad användare är samma
        //        ---
        post = postService.saveNewPost(post);
        return Response.status(CREATED).build();
    }

//    // Paging, vilken sida + antal per sida (default 10 per sida)
//    @GET
//    public Response getPosts(@BeanParam PostParam param,
//                             @CookieParam("name") Cookie cookie){
//        if(cookie == null){
//            return Response.status(UNAUTHORIZED).build();
//        } else{
//            postService.validateCookie(cookie);
//            List<PostComplete> posts = postService.getPostsAndLikesComments(param);
//            return Response.ok(posts).build();
//        }
//    }

    // TEMPORARY UNTIL COOKIE WORKS
    // Paging, vilken sida + antal per sida (default 10 per sida)
    @GET
    public Response getPostsTEMP(@BeanParam PostParam param){
//        if(userNumber == null){
//            return Response.status(UNAUTHORIZED).build();
//        } else{
//            if(postService.validateCookie(userNumber)){
                List<PostComplete> posts = postService.getPostsAndLikesComments(param);
                return Response.ok(posts).build();
//            }
//            return Response.status(UNAUTHORIZED).build();
//        }
    }

    @POST
    @Path("{postNumber}/likes")
    public Response likePost(@PathParam("postNumber") String postNumber){
        postService.saveNewLike(postNumber);
        return null;
    }

}
