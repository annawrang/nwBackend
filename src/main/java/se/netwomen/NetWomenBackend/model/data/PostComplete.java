package se.netwomen.NetWomenBackend.model.data;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostComplete {

    private Post post;
    private int likes;
    private List<Comment> comment = new ArrayList<>();
    private Timestamp creationTimestamp;

    protected PostComplete(){}

    public PostComplete(Post post, int likes, List<Comment> comment) {
        this.post = post;
        this.likes = likes;
        this.comment = comment;
    }

    public PostComplete(Post post, int likes, List<Comment> comment, Timestamp creationTimestamp) {
        this.post = post;
        this.likes = likes;
        this.comment = comment;
        this.creationTimestamp = creationTimestamp;
    }

    public Post getPost() {
        return post;
    }

    public int getLikes() {
        return likes;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }
}
