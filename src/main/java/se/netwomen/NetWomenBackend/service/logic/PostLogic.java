package se.netwomen.NetWomenBackend.service.logic;

import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentMinimum;
import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentReply;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;
import se.netwomen.NetWomenBackend.repository.DTO.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentReplyDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostLikeDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.Parsers.CommentParser;
import se.netwomen.NetWomenBackend.service.Parsers.CommentReplyParser;
import se.netwomen.NetWomenBackend.service.Parsers.PostLikeParser;
import se.netwomen.NetWomenBackend.service.Parsers.PostParser;

import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostLogic {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentReplyRepository commentReplyRepository;

    public PostLogic(UserRepository userRepository, PostRepository postRepository,
                     PostLikeRepository postLikeRepository, CommentRepository commentRepository,
                     CommentReplyRepository commentReplyRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentRepository = commentRepository;
        this.commentReplyRepository = commentReplyRepository;
    }

    public List<PostComplete> makePaging(Iterable iterablePosts, PostParam param) {
        ArrayList<PostDTO> tempPostList = Lists.newArrayList(iterablePosts);
        List<PostComplete> pagedPosts = new ArrayList<>();
        int start = param.page * param.numbersPerPage - param.numbersPerPage;
        int finish = param.numbersPerPage * param.page - 1;
        if (start < tempPostList.size()) {
            for (int i = start; i <= finish; i++) {
                if (i <= (tempPostList.size() - 1)) {
                    List<UserMinimum> likes = postLikeRepository.findAllByPostId(tempPostList.get(i).getId())
                            .stream().map(PostLikeParser::dtoToUserMinimum).collect(Collectors.toList());
                    List<CommentMinimum> comments = commentDTOListToCommentMinimumAndRepliesList(commentRepository.getCommentsByPostId(tempPostList.get(i).getId()));
                    comments.forEach(c -> System.out.println(c.getCommentNumber()));
                    pagedPosts.add(PostParser.postToPostComplete(tempPostList.get(i), likes, comments));
                }
            }
        }
        return pagedPosts;
    }

    public PostComplete getPostComplete(String postNumber) {
        if (validatePostExists(postNumber)) {
            PostDTO postDto = postRepository.findByPostNumber(postNumber).get();
            Post post = PostParser.postDTOToPost(postDto);
            List<CommentMinimum> commentMinimums = commentDTOListToCommentMinimumAndRepliesList(
                    commentRepository.getCommentsByPostId(postDto.getId()));
            List<UserMinimum> likes = postLikeRepository.findAllByPostId(postDto.getId())
                    .stream().map(PostLikeParser::dtoToUserMinimum).collect(Collectors.toList());
            return new PostComplete(post, likes, commentMinimums);
        }
        throw new BadRequestException();
    }

    public boolean validatePostLikeExists(String postNumber, String userNumber) {
        Optional<PostLikeDTO> likeDTO = postLikeRepository.findByPostNumberAndUserNumber(postNumber, userNumber);
        if (likeDTO.isPresent()) {
            return true;
        }
        return false;
    }

    public Post setPostCreationTime(Post post) {
        LocalDateTime localDateTime = LocalDateTime.now();
        post.setDate(localDateTime);
        return post;
    }

    public CommentReplyDTO createPostCommentReply(String commentNumber, String userNumber, String newComment) {
        if (validateUserExists(userNumber) && validateCommentExists(commentNumber)) {
            return createNewPostCommentReply(commentNumber, userNumber, newComment);
        }
        throw new BadRequestException();
    }

    private CommentReplyDTO createNewPostCommentReply(String commentNumber, String userNumber, String newComment) {
        LocalDateTime date = LocalDateTime.now();
        UserDTO user = userRepository.findByUserNumber(userNumber).get();
        CommentDTO comment = commentRepository.findByCommentNumber(commentNumber).get();
        return new CommentReplyDTO(comment, user, newComment, date, UUID.randomUUID().toString());
    }

    public CommentDTO createNewPostComment(String userNumber, String postNumber, String newComment) {
        LocalDateTime date = LocalDateTime.now();
        UserDTO user = userRepository.findByUserNumber(userNumber).get();
        PostDTO post = postRepository.findByPostNumber(postNumber).get();
        return new CommentDTO(user, post, newComment, date, UUID.randomUUID().toString());
    }

    public List<CommentMinimum> commentDTOListToCommentMinimumAndRepliesList(List<CommentDTO> commentDTOS) {
        List<CommentMinimum> commentMinimums = new ArrayList<>();

        commentDTOS.forEach(c -> {
            List<CommentReply> commentReplies = commentReplyRepository.findByHeadCommentNumber(c.getCommentNumber()).stream()
                    .map(CommentReplyParser::commentReplyDTOtoCommentReply).collect(Collectors.toList());
            commentMinimums.add(CommentParser.commentDTOToCommentMinimumWithReplies(c, commentReplies));
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
        if (validatePostExists(postNumber) && validateUserExists(userNumber)) {
            return getUserByUserNumber(userNumber).getUserNumber()
                    .equals(getPostByPostNumber(postNumber).getUser().getUserNumber());
        }
        return false;
    }

    private UserDTO getUserByUserNumber(String userNumber) {
        return userRepository.findByUserNumber(userNumber).get();
    }

    private PostDTO getPostByPostNumber(String postNumber) {
        return postRepository.findByPostNumber(postNumber).get();
    }

    public boolean validateUserExists(String userNumber) {
        Optional<UserDTO> user = userRepository.findByUserNumber(userNumber);
        return user.isPresent();
    }

    public boolean validatePostExists(String postNumber) {
        Optional<PostDTO> post = postRepository.findByPostNumber(postNumber);
        return post.isPresent();
    }

    public boolean validateCommentExists(String commentNumber) {
        Optional<CommentDTO> comment = commentRepository.findByCommentNumber(commentNumber);
        return comment.isPresent();
    }
}
