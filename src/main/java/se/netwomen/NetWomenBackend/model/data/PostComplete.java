package se.netwomen.NetWomenBackend.model.data;

import org.springframework.stereotype.Component;

@Component
public class PostComplete {

    private Post post;
    private int likes;

    protected PostComplete(){}

    public PostComplete(Post post, int likes) {
        this.post = post;
        this.likes = likes;
    }

    public Post getPost() {
        return post;
    }

    public int getLikes() {
        return likes;
    }
}
