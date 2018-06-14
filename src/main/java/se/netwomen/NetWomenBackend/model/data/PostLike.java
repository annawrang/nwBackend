package se.netwomen.NetWomenBackend.model.data;

import java.sql.Timestamp;

public final class PostLike {

    private User user;
    private Post post;
    private Timestamp creationTimestamp;

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public PostLike(User user, Post post, Timestamp creationTimestamp) {
        this.user = user;
        this.post = post;
        this.creationTimestamp = creationTimestamp;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }
}
