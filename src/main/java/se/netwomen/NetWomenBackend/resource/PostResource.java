package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

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

    @POST
    public Response createNewPost(Post post){

        return Response.ok().build();
    }

    // Paging, vilken sida + antal per sida (default 10 per sida)
    @GET
    public Response getPosts(@BeanParam PostParam param){
//        if(postService.validateCookie(requestHeaders)) {
            List<PostComplete> posts = postService.getPostsAndLikes(param);

            return Response.ok(posts).build();
        }
//        return Response.status(Response.Status.UNAUTHORIZED).build();
//    }

}
