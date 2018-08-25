package se.netwomen.NetWomenBackend.model.data.PostComplete;

import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.User;

public final class PostLike {

    private User user;
    private Post post;

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
    }


    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

}
