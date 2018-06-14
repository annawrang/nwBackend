package se.netwomen.NetWomenBackend.service;

import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;
import se.netwomen.NetWomenBackend.model.data.PostComplete;
import se.netwomen.NetWomenBackend.repository.DTO.CommentRepository;
import se.netwomen.NetWomenBackend.repository.DTO.PostLikeRepository;
import se.netwomen.NetWomenBackend.repository.DTO.PostRepository;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.Parsers.PostParser;
import se.netwomen.NetWomenBackend.service.exceptions.InvalidCookieException;

import javax.ws.rs.core.Cookie;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository,
                       PostLikeRepository postLikeRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentRepository = commentRepository;
    }

    public List<PostComplete> getPostsAndLikesComments(PostParam param) {
        if (param.page != null) {
            return makePaging(postRepository.findAllOrderedByTimeStamp(), param);
        } else {
            List<PostComplete> posts = PostParser.postDTOToPostCompleteList(postRepository.findAll());
            return posts;
        }
    }

    private List<PostComplete> makePaging(Iterable iterablePosts, PostParam param) {
        List<PostDTO> tempPostList = Lists.newArrayList(iterablePosts);
        List<PostComplete> pagedPosts = new ArrayList<>();
        int start = param.page * param.numbersPerPage - param.numbersPerPage;
        int finish = param.numbersPerPage * param.page - 1;
        if (start < tempPostList.size()) {
            for (int i = start; i <= finish; i++) {
                if (i <= (tempPostList.size() - 1)) {
                    int likesCount = postLikeRepository.countPostLikesByPostId(tempPostList.get(i).getId());
                    List<CommentDTO> commentDTOS = commentRepository.getCommentsByPostId(tempPostList.get(i).getId());
                    pagedPosts.add(PostParser.postToPostComplete(tempPostList.get(i), likesCount, commentDTOS));
                }
            }
        }
        return pagedPosts;
    }

    // Checks that the cookie exists in the database
//    public boolean validateCookie(Cookie cookie) {
//        if (cookie.getValue() != null) {
//            System.out.println(cookie.getValue());
//            Optional<UserDTO> user = userRepository.findUsersCookie(cookie.getValue());
//            if (user.isPresent())
//                return true;
//        }
//        throw new InvalidCookieException();
//    }


    // FUNKAR EJ ATT SPARA POST MED EN USER!
    public Post saveNewPost(Post post) {
        post = setCreationTime(post);
        PostDTO postDTO = PostParser.postToPostDTO(post);
        Optional<UserDTO> userDTO = userRepository.findByUserName(postDTO.getUser().getUserName());
        if(userDTO.isPresent()){
            postDTO.setUserDTO(userDTO.get());
        }
            postDTO = postRepository.save(postDTO);

        return post;
    }

    public Post setCreationTime(Post post){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post.setCreationTimestamp(timestamp);
        return post;
    }
}
