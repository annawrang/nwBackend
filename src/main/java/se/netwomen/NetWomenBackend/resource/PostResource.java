package se.netwomen.NetWomenBackend.resource;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("post")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final PostService postService;

    public PostResource(PostService service){
        this.postService = service;
    }

    @POST
    public Response createNewPost(Post post){

        return null;
    }

    // Paging
    @GET
    public Response getPosts(@BeanParam PostParam param){
        List<Post> posts = postService.getPosts(param);
        return null;
    }
}
