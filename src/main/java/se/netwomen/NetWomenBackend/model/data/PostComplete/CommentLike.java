package se.netwomen.NetWomenBackend.model.data.PostComplete;

import se.netwomen.NetWomenBackend.model.data.User;

import java.time.LocalDateTime;

public class CommentLike {

    private UserMinimum user;
    private CommentMinimum comment;
    private String commentLikeNumber;
    private LocalDateTime date;

    public CommentLike(UserMinimum user, CommentMinimum comment, String commentLikeNumber, LocalDateTime date) {
        this.user = user;
        this.comment = comment;
        this.commentLikeNumber = commentLikeNumber;
        this.date = date;
    }

    public UserMinimum getUser() {
        return user;
    }

    public CommentMinimum getComment() {
        return comment;
    }

    public String getCommentLikeNumber() {
        return commentLikeNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
