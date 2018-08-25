package se.netwomen.NetWomenBackend.service;

import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.repository.DTO.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentReplyDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostLikeDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.Parsers.PostParser;
import se.netwomen.NetWomenBackend.service.logic.PostLogic;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final PostLogic postLogic;

    public PostService(PostRepository postRepository, UserRepository userRepository,
                       PostLikeRepository postLikeRepository, CommentRepository commentRepository,
                       CommentReplyRepository commentReplyRepository, PostLogic postLogic) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentRepository = commentRepository;
        this.commentReplyRepository = commentReplyRepository;
        this.postLogic = postLogic;
    }

    public List<PostComplete> getPostsAndLikesComments(PostParam param) {
        if (param.page != null) {
            return postLogic.makePaging(postRepository.findAllOrderedByDate(), param);
        } else {
            return PostParser.postDTOToPostCompleteList(postRepository.findAll());
        }
    }


    public void saveNewPost(String userNumber, Post post) {
        if (postLogic.validateUserExists(userNumber)) {
            Optional<UserDTO> userDTO = userRepository.findByUserNumber(userNumber);
            post = postLogic.setPostCreationTime(post);
            post.setPostNumber(UUID.randomUUID().toString());
            PostDTO postDTO = PostParser.postToPostDTO(post);
            postDTO = postRepository.save(postDTO.setUserDTO(userDTO.get()));
        } else {
            throw new BadRequestException();
        }
    }

    public void saveNewPostLike(String postNumber, String userNumber) {
        if (postLogic.validateUserExists(userNumber) && postLogic.validatePostExists(postNumber)) {
            if (postLogic.validatePostLikeExists(postNumber, userNumber)) {
                postLikeRepository.delete(postLikeRepository.findByPostNumberAndUserNumber(postNumber, userNumber).get());
            } else {
                PostLikeDTO newLikeDTO = new PostLikeDTO(userRepository.findByUserNumber(userNumber).get(),
                        postRepository.findByPostNumber(postNumber).get());
                newLikeDTO = postLikeRepository.save(newLikeDTO);
            }
        } else {
            throw new BadRequestException();
        }
    }


    public void editPost(String postNumber, String userNumber, String newText) {
        if (postLogic.validatePostExists(postNumber) && postLogic.validateUserExists(userNumber) &&
                postLogic.validatePostIsWrittenByUser(postNumber, userNumber)) {
            Optional<PostDTO> post = postRepository.findByPostNumber(postNumber);
            post.get().setText(newText);
            postRepository.save(post.get());
        } else {
            throw new BadRequestException();
        }
    }

    public void createPostComment(String postNumber, String userNumber, String newComment) {
        if (postLogic.validatePostExists(postNumber) && postLogic.validateUserExists(userNumber)) {
            CommentDTO commentDTO = postLogic.createNewPostComment(userNumber, postNumber, newComment);
            commentRepository.save(commentDTO);
        } else {
            throw new BadRequestException();
        }
    }


    public void deletePost(String postNumber, String userNumber) {
        if (postLogic.validateUserExists(userNumber) && postLogic.validatePostExists(postNumber) &&
                postLogic.validatePostIsWrittenByUser(postNumber, userNumber)) {
            Optional<PostDTO> post = postRepository.findByPostNumber(postNumber);
            postLogic.deleteCommentsAndPostLike(post.get());
            postRepository.delete(post.get());
        } else {
            throw new BadRequestException();
        }
    }


    public PostComplete getPost(String postNumber) {
        return postLogic.getPostComplete(postNumber);
    }

    public void createPostCommentReply(String commentNumber, String userNumber, String newComment) {
        if (postLogic.validateUserExists(userNumber) && postLogic.validateCommentExists(commentNumber)) {
            CommentReplyDTO reply = postLogic.createPostCommentReply(commentNumber, userNumber, newComment);
            commentReplyRepository.save(reply);
        } else {
            throw new BadRequestException();
        }
    }
}
