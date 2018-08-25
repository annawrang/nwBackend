package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentMinimum;
import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.ArrayList;
import java.util.List;

public final class PostParser {

    public static List<PostComplete> postDTOToPostCompleteList(Iterable<PostDTO> postList){
        List<PostComplete> postCompletes = new ArrayList<>();
        postList.forEach(p -> {
            Post temp = PostParser.postDTOToPost(p);
            postCompletes.add(new PostComplete(temp, new ArrayList<>(), new ArrayList<>(), p.getDate()));
        });
        return postCompletes;
    }

    public static Post postDTOToPost(PostDTO postDTO) {
        return new Post(UserParser.toUserMinimum(postDTO.getUser()), postDTO.getText(), postDTO.getPictureUrl(), postDTO.getDate(), postDTO.getPostNumber());
    }


    public static PostComplete postToPostComplete(PostDTO post, List<UserMinimum> likes, List<CommentMinimum> comments){
        Post temp = PostParser.postDTOToPost(post);
        return new PostComplete(temp, likes, comments, post.getDate());
    }

    public static PostDTO postToPostDTO(Post post) {
        return new PostDTO(new UserDTO("","",""), post.getPictureUrl(), post.getText(), post.getDate(), post.getPostNumber());
    }
}
