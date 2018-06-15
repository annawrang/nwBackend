package se.netwomen.NetWomenBackend.model.data;

import java.sql.Timestamp;

public final class Comment {

    private User user;
    private Post post;
    private String text;
    private Timestamp createdTimestamp;

    public Comment(User user, Post post, String text) {
        this.user = user;
        this.post = post;
        this.text = text;
    }

    public Comment(User user, Post post, String text, Timestamp createdTimestamp) {
        this.user = user;
        this.post = post;
        this.text = text;
        this.createdTimestamp = createdTimestamp;
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

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}
