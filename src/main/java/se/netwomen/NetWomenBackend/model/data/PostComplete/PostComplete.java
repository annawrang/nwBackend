package se.netwomen.NetWomenBackend.model.data.PostComplete;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostComplete {

    private Post post;
    private int likes;
    private List<CommentMinimum> comment = new ArrayList<>();
    private Timestamp creationTimestamp;

    protected PostComplete(){}

    public PostComplete(Post post, int likes, List<CommentMinimum> comment) {
        this.post = post;
        this.likes = likes;
        this.comment = comment;
    }

    public PostComplete(Post post, int likes, List<CommentMinimum> comment, Timestamp creationTimestamp) {
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

    public List<CommentMinimum> getComment() {
        return comment;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }
}
