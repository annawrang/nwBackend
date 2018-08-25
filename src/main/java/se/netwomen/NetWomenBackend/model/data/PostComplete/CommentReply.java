package se.netwomen.NetWomenBackend.model.data.PostComplete;

import java.time.LocalDateTime;

public class CommentReply {
    private UserMinimum user;
    private String text;
    private LocalDateTime date;
    private String commentNumber;

    public CommentReply(UserMinimum user, String text, LocalDateTime date, String commentNumber) {
        this.user = user;
        this.text = text;
        this.date = date;
        this.commentNumber = commentNumber;
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

    public String getCommentNumber() {
        return commentNumber;
    }
}
