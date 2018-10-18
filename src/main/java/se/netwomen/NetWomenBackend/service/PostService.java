package se.netwomen.NetWomenBackend.service;

import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.post.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.user.UserRepository;
import se.netwomen.NetWomenBackend.controller.param.PostParam;
import se.netwomen.NetWomenBackend.service.Parsers.PostParser;
import se.netwomen.NetWomenBackend.service.logic.PostLogic;
import se.netwomen.NetWomenBackend.service.logic.UserLogic;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final CommentReplyLikeRepository replyLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLogic postLogic;
    private final UserLogic userLogic;

    public PostService(PostRepository postRepository, UserRepository userRepository,
                       PostLikeRepository postLikeRepository, CommentRepository commentRepository,
                       CommentReplyLikeRepository replyLikeRepository, CommentLikeRepository commentLikeRepository,
                       CommentReplyRepository commentReplyRepository, PostLogic postLogic,
                       UserLogic userLogic) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentRepository = commentRepository;
        this.commentReplyRepository = commentReplyRepository;
        this.replyLikeRepository = replyLikeRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.postLogic = postLogic;
        this.userLogic = userLogic;
    }

    public List<PostComplete> getPostsAndLikesComments(int page, int numbersPerPage) {
            return postLogic.makePaging(postRepository.findAllOrderedByDate(), page, numbersPerPage);

    }

    public List<PostComplete> getUsersPosts(String userNumber, int page, int numbersPerPage) {
        return postLogic.makePaging(postRepository.findByUserNumber(userNumber, page, numbersPerPage), page, numbersPerPage);
    }


    public void saveNewPost(String userNumber, Post post) {
        UserDTO userDTO = userLogic.validateUserExists(userNumber);
        post = postLogic.setPostCreationTime(post);
        post.setPostNumber(UUID.randomUUID().toString());
        PostDTO postDTO = PostParser.postToPostDTO(post);
        postDTO = postRepository.save(postDTO.setUserDTO(userDTO));
    }

    public void saveNewPostLike(String postNumber, String userNumber) {
        UserDTO user = userLogic.validateUserExists(userNumber);
        PostDTO post = postLogic.validatePostExists(postNumber);
        if (postLogic.doesPostLikeExists(postNumber, userNumber)) {
            postLikeRepository.delete(postLogic.validatePostLikeExists(postNumber, userNumber));
        } else {
            PostLikeDTO newLikeDTO = new PostLikeDTO(user, post);
            newLikeDTO = postLikeRepository.save(newLikeDTO);
        }
    }


    public void editPost(String postNumber, String userNumber, String newText) {
        PostDTO post = postLogic.validatePostExists(postNumber);
        userLogic.validateUserExists(userNumber);
        postLogic.validatePostIsWrittenByUser(postNumber, userNumber);
        post = post.setText(newText);
        postRepository.save(post);

    }

    public void createPostComment(String postNumber, String userNumber, String newComment) {
        CommentDTO commentDTO = postLogic.createNewPostComment(userNumber, postNumber, newComment);
        commentRepository.save(commentDTO);
    }


    public void deletePost(String postNumber, String userNumber) {
        PostDTO post = postLogic.validatePostExists(postNumber);
        userLogic.validateUserExists(userNumber);
        postLogic.validatePostIsWrittenByUser(postNumber, userNumber);

        postLogic.deleteCommentsAndPostLike(post);
        postRepository.delete(post);
    }


    public PostComplete getPost(String postNumber) {
        return postLogic.getPostComplete(postNumber);
    }

    public void createPostCommentReply(String commentNumber, String userNumber, String newComment) {
        CommentReplyDTO reply = postLogic.createPostCommentReply(commentNumber, userNumber, newComment);
        commentReplyRepository.save(reply);
    }


    public void createPostCommentLike(String userNumber, String postNumber, String commentNumber) {
        userLogic.validateUserExists(userNumber);
        postLogic.validatePostExists(postNumber);
        postLogic.validateCommentExists(commentNumber);
        if (postLogic.doesCommentLikeExists(commentNumber, userNumber)) {
            deleteCommentLike(commentNumber, userNumber);
        } else {
            CommentLikeDTO newLike = postLogic.createCommentLike(userNumber, commentNumber);
            commentLikeRepository.save(newLike);
        }
    }

    private void deleteCommentLike(String commentNumber, String userNumber) {
        CommentLikeDTO like = postLogic.validateCommentLikeExists(commentNumber, userNumber);
        commentLikeRepository.delete(like);
    }

//    private void deleteReplyLike(String replyNumber, String userNumber) {
//        CommentReplyLikeDTO like = replyLikeRepository.findByCommentReplyNumber(replyNumber).get();
//        replyLikeRepository.delete(like);
//    }
}
