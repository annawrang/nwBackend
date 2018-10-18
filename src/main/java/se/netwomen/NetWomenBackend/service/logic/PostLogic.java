package se.netwomen.NetWomenBackend.service.logic;

import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.post.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.user.UserRepository;
import se.netwomen.NetWomenBackend.controller.param.PostParam;
import se.netwomen.NetWomenBackend.service.Parsers.*;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostLogic {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentReplyLikeRepository replyLikeRepository;

    public PostLogic(UserRepository userRepository, PostRepository postRepository,
                     PostLikeRepository postLikeRepository, CommentRepository commentRepository,
                     CommentReplyRepository commentReplyRepository, CommentLikeRepository commentLikeRepository,
                     CommentReplyLikeRepository replyLikeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentRepository = commentRepository;
        this.commentReplyRepository = commentReplyRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.replyLikeRepository = replyLikeRepository;
    }

    public List<PostComplete> makePaging(Iterable iterablePosts, int page, int numbersPerPage) {
        ArrayList<PostDTO> tempPostList = Lists.newArrayList(iterablePosts);
        List<PostComplete> pagedPosts = new ArrayList<>();
        int start = page * numbersPerPage - numbersPerPage;
        int finish = numbersPerPage * page - 1;
        if (start < tempPostList.size()) {
            for (int i = start; i <= finish; i++) {
                if (i <= (tempPostList.size() - 1)) {
                    List<UserMinimum> likes = postLikeRepository.findAllByPostId(tempPostList.get(i).getId())
                            .stream().map(PostLikeParser::dtoToUserMinimum).collect(Collectors.toList());
                    List<CommentMinimum> comments = commentDTOListToCommentMinimumAndRepliesList(commentRepository.getCommentsByPostId(tempPostList.get(i).getId()));
                    pagedPosts.add(PostParser.postToPostComplete(tempPostList.get(i), likes, comments));
                }
            }
        }
        return pagedPosts;
    }

    public PostComplete getPostComplete(String postNumber) {
        PostDTO postDto = validatePostExists(postNumber);
        Post post = PostParser.postDTOToPost(postDto);
        List<CommentMinimum> commentMinimums = commentDTOListToCommentMinimumAndRepliesList(
                commentRepository.getCommentsByPostId(postDto.getId()));
        List<UserMinimum> likes = postLikeRepository.findAllByPostId(postDto.getId())
                .stream().map(PostLikeParser::dtoToUserMinimum).collect(Collectors.toList());
        return new PostComplete(post, likes, commentMinimums);
    }

    public PostLikeDTO validatePostLikeExists(String postNumber, String userNumber) {
        return postLikeRepository.findByPostNumberAndUserNumber(postNumber, userNumber).orElseThrow(NotFoundException::new);
    }

    public boolean doesPostLikeExists(String postNumber, String userNumber){
        return postLikeRepository.findByPostNumberAndUserNumber(postNumber, userNumber).isPresent();
    }

    public Post setPostCreationTime(Post post) {
        LocalDateTime localDateTime = LocalDateTime.now();
        post.setDate(localDateTime);
        return post;
    }

    public CommentReplyDTO createPostCommentReply(String commentNumber, String userNumber, String newComment) {
        LocalDateTime date = LocalDateTime.now();
        UserDTO user = validateUserExists(userNumber);
        CommentDTO comment = validateCommentExists(commentNumber);
        return new CommentReplyDTO(comment, user, newComment, date, UUID.randomUUID().toString());

    }

    public CommentDTO createNewPostComment(String userNumber, String postNumber, String newComment) {
        LocalDateTime date = LocalDateTime.now();
        UserDTO user = validateUserExists(userNumber);
        PostDTO post = validatePostExists(postNumber);
        return new CommentDTO(user, post, newComment, date, UUID.randomUUID().toString());
    }

    public List<CommentMinimum> commentDTOListToCommentMinimumAndRepliesList(List<CommentDTO> commentDTOS) {
        List<CommentMinimum> commentMinimums = new ArrayList<>();

        commentDTOS.forEach(c -> {
            List<CommentReply> commentReplies = commentReplyRepository.findByHeadCommentNumber(c.getCommentNumber()).stream()
                    .map(CommentReplyParser::commentReplyDTOtoCommentReply).collect(Collectors.toList());

            List<CommentLike> commentLikes = commentLikeRepository.findByCommentNumber(c.getCommentNumber()).stream()
                    .map(CommentLikeParser::commentLikeDTOtoCommentLike).collect(Collectors.toList());

            commentMinimums.add(CommentParser.commentDTOToCommentMinimumWithRepliesLikes(c, commentReplies, commentLikes));
        });
        return commentMinimums;
    }

    public void deleteCommentsAndPostLike(PostDTO post) {
        List<PostLikeDTO> postLikes = postLikeRepository.findAllByPostId(post.getId());
        postLikes.forEach(postLikeRepository::delete);
        List<CommentDTO> comments = commentRepository.getCommentsByPostId(post.getId());
        comments.forEach(commentRepository::delete);
    }

    public boolean validatePostIsWrittenByUser(String postNumber, String userNumber) {
        return validateUserExists(userNumber).getUserNumber()
                .equals(validatePostExists(postNumber).getUser().getUserNumber());
    }

    public UserDTO validateUserExists(String userNumber) {
        return userRepository.findByUserNumber(userNumber).orElseThrow(NotFoundException::new);
    }

    public PostDTO validatePostExists(String postNumber) {
        return postRepository.findByPostNumber(postNumber).orElseThrow(NotFoundException::new);
    }

    public CommentDTO validateCommentExists(String commentNumber) {
        return commentRepository.findByCommentNumber(commentNumber).orElseThrow(NotFoundException::new);
    }

//    public CommentReplyDTO validateCommentReplyExist(String replyNumber) {
//        return commentReplyRepository.findByCommentReplyNumber(replyNumber).orElseThrow(NotFoundException::new);
//    }
//
//    public CommentReplyLikeDTO createCommentReplyLike(String userNumber, String replyNumber) {
//        LocalDateTime date = LocalDateTime.now();
//        UserDTO user = userRepository.findByUserNumber(userNumber).get();
//        CommentReplyDTO reply = commentReplyRepository.findByCommentReplyNumber(replyNumber).get();
//        return new CommentReplyLikeDTO(reply, user, date, UUID.randomUUID().toString());
//    }

    public CommentLikeDTO validateCommentLikeExists(String commentNumber, String userNumber) {
        return commentLikeRepository.findByCommentNumberAndUserNumber(commentNumber, userNumber).orElseThrow(NotFoundException::new);
    }

    public CommentLikeDTO createCommentLike(String userNumber, String commentNumber) {
        LocalDateTime date = LocalDateTime.now();
        UserDTO user = validateUserExists(userNumber);
        CommentDTO comment = validateCommentExists(commentNumber);
        return new CommentLikeDTO(user, comment, date, UUID.randomUUID().toString());
    }

    public boolean doesCommentLikeExists(String commentNumber, String userNumber) {
        return commentLikeRepository.findByCommentNumberAndUserNumber(commentNumber, userNumber).isPresent();
    }
}
