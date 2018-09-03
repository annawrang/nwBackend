package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Comment;
import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentReply;
import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentMinimum;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;

import java.util.ArrayList;
import java.util.List;

public final class CommentParser {

    public static Comment commentDTOTOComment(CommentDTO commentDTO){
        return new Comment(UserParser.toUser(commentDTO.getUser()), PostParser.postDTOToPost(commentDTO.getPost()),
                commentDTO.getText(), commentDTO.getDate(), commentDTO.getCommentNumber());
    }

    public static CommentDTO commentToCommentDTO(Comment comment){
        return new CommentDTO(UserParser.toUserDTO(comment.getUser()), PostParser.postToPostDTO(comment.getPost()),
                comment.getText(), comment.getCreatedDate(), comment.getCommentNumber());
    }

    public static List<Comment> commentDTOListToCommentList(List<CommentDTO> commentDTOS) {
        List<Comment> commentList = new ArrayList<>();
        commentDTOS.forEach(c -> commentList.add(CommentParser.commentDTOTOComment(c)));
        return commentList;
    }

//    public static List<CommentMinimum> commentDTOListToCommentMinimumList(List<CommentDTO> commentDTOS) {
//        List<CommentMinimum> commentMinimums = new ArrayList<>();
//        commentDTOS.forEach(c -> commentMinimums.add(CommentParser.commentDTOToCommentMinimum(c)));
//        return commentMinimums;
//
//    }

    public static CommentMinimum commentDTOToCommentMinimumNoReplies(CommentDTO c) {
        return new CommentMinimum(UserParser.toUserMinimum(c.getUser()), c.getText(), c.getDate());
    }

    public static CommentMinimum commentDTOToCommentMinimumWithReplies(CommentDTO c, List<CommentReply> commentReplies) {
        return new CommentMinimum(UserParser.toUserMinimum(c.getUser()), c.getText(), c.getDate(), commentReplies, c.getCommentNumber());
    }
}
