package se.netwomen.NetWomenBackend.model.data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public final class Comment {

    private User user;
    private Post post;
    private String text;
    private LocalDateTime date;
    private String commentNumber;

    public Comment(User user, Post post, String text) {
        this.user = user;
        this.post = post;
        this.text = text;
    }

    public Comment(User user, Post post, String text, LocalDateTime date, String commentNumber) {
        this.user = user;
        this.post = post;
        this.text = text;
        this.date = date;
        this.commentNumber = commentNumber;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedDate() {
        return date;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCreatedDate(LocalDateTime date) {
        this.date = date;
    }
}
