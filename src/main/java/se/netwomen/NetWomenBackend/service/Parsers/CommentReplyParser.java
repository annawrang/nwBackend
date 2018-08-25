package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentReply;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentReplyDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CommentReplyParser {

    public static CommentReply commentReplyDTOtoCommentReply(CommentReplyDTO c){
        return new CommentReply(UserParser.toUserMinimum(c.getUser()),
                c.getText(), c.getDate(), c.getCommentReplyNumber());
    }

    public static List<CommentReply> commentReplyDTOListToCommentReplyList(List<CommentReplyDTO> cList){
        return cList.stream().map(CommentReplyParser::commentReplyDTOtoCommentReply).collect(Collectors.toList());
    }
}
