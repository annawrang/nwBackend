package se.netwomen.NetWomenBackend.model.data.PostComplete;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostComplete {

    private Post post;
    private List<UserMinimum> likes = new ArrayList<>();
    private List<CommentMinimum> comment = new ArrayList<>();
    private LocalDateTime date;

    protected PostComplete() {
    }

    public PostComplete(Post post, List<UserMinimum> likes, List<CommentMinimum> comment) {
        this.post = post;
        this.likes = likes;
        this.comment = comment;
    }

    public PostComplete(Post post, List<UserMinimum> likes, List<CommentMinimum> comment, LocalDateTime date) {
        this.post = post;
        this.likes = likes;
        this.comment = comment;
        this.date = date;
    }

    public Post getPost() {
        return post;
    }

    public List<UserMinimum> getLikes() {
        return likes;
    }

    public List<CommentMinimum> getComment() {
        return comment;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
