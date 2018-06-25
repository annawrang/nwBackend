package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.ArrayList;
import java.util.List;

public final class PostParser {

    public static List<PostComplete> postDTOToPostCompleteList(List<PostDTO> postList){
        List<PostComplete> postCompletes = new ArrayList<>();
        postList.forEach(p -> {
            Post temp = PostParser.postDTOToPost(p);
            postCompletes.add(new PostComplete(temp, 0, new ArrayList<>(), p.getCreationTimestamp()));
        });
        return postCompletes;
    }

    public static List<PostComplete> postDTOToPostCompleteList(Iterable<PostDTO> postList){
        List<PostComplete> postCompletes = new ArrayList<>();
        postList.forEach(p -> {
            Post temp = PostParser.postDTOToPost(p);
            postCompletes.add(new PostComplete(temp, 0, new ArrayList<>(), p.getCreationTimestamp()));
        });
        return postCompletes;
    }

    public static Post postDTOToPost(PostDTO postDTO) {
        return new Post(UserParser.toUserMinimum(postDTO.getUser()), postDTO.getText(), postDTO.getPictureUrl(), postDTO.getCreationTimestamp(), postDTO.getPostNumber());
    }


    public static PostComplete postToPostComplete(PostDTO post, int likes, List<CommentDTO> commentDTOS){
        Post temp = PostParser.postDTOToPost(post);
        return new PostComplete(temp, likes, CommentParser.commentDTOListToCommentMinimumList(commentDTOS), post.getCreationTimestamp());
    }

    public static PostDTO postToPostDTO(Post post) {
        return new PostDTO(new UserDTO("","",""), post.getPictureUrl(), post.getText(), post.getCreationTimestamp(), post.getPostNumber());
    }
}
