package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentLike;
import se.netwomen.NetWomenBackend.model.data.PostComplete.CommentMinimum;
import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentLikeDTO;

public class CommentLikeParser {


    public static CommentLike commentLikeDTOtoCommentLike(CommentLikeDTO c) {
        UserMinimum user = UserParser.toUserMinimum(c.getUser());
        CommentMinimum comment = CommentParser.commentDTOToCommentMinimumNoReplies(c.getComment());

        return new CommentLike(user, comment, c.getCommentLikeNumber(), c.getDate());
    }
}
