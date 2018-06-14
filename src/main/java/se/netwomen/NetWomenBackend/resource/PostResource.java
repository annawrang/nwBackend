package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;
import se.netwomen.NetWomenBackend.model.data.PostComplete;
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

    @POST
    public Response createNewPost(Post post){
        post = postService.saveNewPost(post);
            return Response.status(CREATED).build();
    }

    // Paging, vilken sida + antal per sida (default 10 per sida)
    @GET
    public Response getPosts(@BeanParam PostParam param){
//                             @CookieParam("name") Cookie cookie
//        if(cookie == null){
//            return Response.status(Response.Status.CREATED).build();
//        } else{
//            postService.validateCookie(cookie);
            List<PostComplete> posts = postService.getPostsAndLikesComments(param);
            return Response.ok(posts).build();
//        }
    }

}
