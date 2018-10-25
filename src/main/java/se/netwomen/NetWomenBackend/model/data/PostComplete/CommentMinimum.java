package se.netwomen.NetWomenBackend.model.data.PostComplete;

import java.time.LocalDateTime;
import java.util.List;

public class CommentMinimum {
    private UserMinimum user;
    private String text;
    private LocalDateTime date;
    private List<CommentReply> commentReplies;
    private String commentNumber;
    private List<CommentLike> commentLikes;

    public CommentMinimum(UserMinimum user, String text, LocalDateTime date, List<CommentReply> commentReplies,
                          String commentNumber, List<CommentLike> commentLikes) {
        this.user = user;
        this.text = text;
        this.date = date;
        this.commentReplies = commentReplies;
        this.commentNumber = commentNumber;
        this.commentLikes = commentLikes;
    }

    public CommentMinimum(UserMinimum user, String text, LocalDateTime date) {
        this.user = user;
        this.text = text;
        this.date = date;
    }

    public UserMinimum getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<CommentReply> getCommentReplies() {
        return commentReplies;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        this.commentNumber = commentNumber;
    }

    public List<CommentLike> getCommentLikes() {
        return commentLikes;
    }
}
