package se.netwomen.NetWomenBackend.service;

import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentMinimum;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.repository.DTO.CommentRepository;
import se.netwomen.NetWomenBackend.repository.DTO.PostLikeRepository;
import se.netwomen.NetWomenBackend.repository.DTO.PostRepository;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostLikeDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.Parsers.CommentParser;
import se.netwomen.NetWomenBackend.service.Parsers.PostParser;
import se.netwomen.NetWomenBackend.service.exceptions.InvalidCookieException;

import javax.ws.rs.BadRequestException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
//                    int commentsCount = commentDTOS.size() + 1;
                    pagedPosts.add(PostParser.postToPostComplete(tempPostList.get(i), likesCount, commentDTOS));
                }
            }
        }
        return pagedPosts;
    }



    // FUNKAR EJ ATT SPARA POST MED EN USER!
    public void saveNewPost(String userNumber, Post post) {
        Optional<UserDTO> userDTO = userRepository.findByUserNumber(userNumber);
        if (userDTO.isPresent()) {
            post = setCreationTime(post);
            post.setPostNumber(UUID.randomUUID().toString());
            PostDTO postDTO = PostParser.postToPostDTO(post);
            postDTO.setUserDTO(userDTO.get());
            postDTO = postRepository.save(postDTO);
        } else {
            throw new BadRequestException();
        }
    }

    private Post setCreationTime(Post post) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post.setCreationTimestamp(timestamp);
        return post;
    }

    // Returns true if the PostLike has been saved (has an Id)
    public void saveNewPostLike(String postNumber, String userNumber) {
        Optional<PostDTO> postDTO = postRepository.findByPostNumber(postNumber);
        Optional<UserDTO> userDTO = userRepository.findByUserNumber(userNumber);

        if (postDTO.isPresent() && userDTO.isPresent()) {
            Optional<PostLikeDTO> likeDTO = postLikeRepository.findByPostNumberAndUserNumber(postNumber, userNumber);
            if (likeDTO.isPresent()) {
                postLikeRepository.delete(likeDTO.get());
            } else {
                PostLikeDTO newLikeDTO = this.createNewPostLike(userDTO.get(), postDTO.get());
                newLikeDTO = postLikeRepository.save(newLikeDTO);
            }
        }

    }

    private PostLikeDTO createNewPostLike(UserDTO user, PostDTO post) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return new PostLikeDTO(user, post, now);
    }

    private CommentDTO createNewComment(String userNumber, String postNumber, String newComment){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        UserDTO user = userRepository.findByUserNumber(userNumber).get();
        PostDTO post = postRepository.findByPostNumber(postNumber).get();
        return new CommentDTO(user, post, newComment, now);
    }

    public boolean editPost(String postNumber, String userNumber, String newText) {
        if (validatePostAndAuthor(postNumber, userNumber)) {
            Optional<PostDTO> post = postRepository.findByPostNumber(postNumber);
            if (post.isPresent()) {
                post.get().setText(newText);
                postRepository.save(post.get());
                return true;
            }
        }
        return false;
    }

    public boolean createPostComment(String postNumber, String userNumber, String newComment) {
        if (validatePostExists(postNumber) && validateUserExists(userNumber)) {
            CommentDTO commentDTO = createNewComment(userNumber, postNumber, newComment);
            commentDTO = commentRepository.save(commentDTO);
            if(commentDTO.getId() != null){
                return true;
            }
        }
        return false;
    }

    private boolean validateUserExists(String userNumber) {
        Optional<UserDTO> user = userRepository.findByUserNumber(userNumber);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    private boolean validatePostExists(String postNumber) {
        Optional<PostDTO> post = postRepository.findByPostNumber(postNumber);
        if (post.isPresent()) {
            return true;
        }
        return false;
    }

    // Returns true if the post is deleted
    public boolean deletePost(String postNumber, String userNumber) {
        if (validatePostAndAuthor(postNumber, userNumber)) {
            Optional<PostDTO> post = postRepository.findByPostNumber(postNumber);
            if (post.isPresent()) {
                deleteCommentsAndPostLike(post.get());
                postRepository.delete(post.get());
                return true;
            }
        }
        return false;
    }

    private boolean validatePostAndAuthor(String postNumber, String userNumber) {
        Optional<UserDTO> user = userRepository.findByUserNumber(userNumber);
        Optional<PostDTO> post = postRepository.findByPostNumber(postNumber);
        if (user.isPresent() && post.isPresent()) {
            if (user.get().getUserNumber().equals(post.get().getUser().getUserNumber())) {
                return true;
            }
        }
        return false;
    }

    private void deleteCommentsAndPostLike(PostDTO post) {
        List<PostLikeDTO> postLikes = postLikeRepository.findAllByPostId(post.getId());
        postLikes.forEach(postLikeDTO -> postLikeRepository.delete(postLikeDTO));
        List<CommentDTO> comments = commentRepository.getCommentsByPostId(post.getId());
        comments.forEach(commentDTO -> commentRepository.delete(commentDTO));
    }

    public PostComplete getPost(String postNumber) {
        if(validatePostExists(postNumber)){
            PostDTO postDto = postRepository.findByPostNumber(postNumber).get();
            Post post = PostParser.postDTOToPost(postDto);
            List<CommentDTO> commentDTOS = commentRepository.getCommentsByPostId(postDto.getId());
            List<CommentMinimum> commentMinimums = CommentParser.commentDTOListToCommentMinimumList(commentDTOS);
            int  likesCount = postLikeRepository.countPostLikesByPostId(postDto.getId());
            return new PostComplete(post, likesCount, commentMinimums);
        }
        return null;
    }
}
