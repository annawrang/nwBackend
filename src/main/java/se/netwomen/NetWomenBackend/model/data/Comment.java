package se.netwomen.NetWomenBackend.model.data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
    private String text;
    private Timestamp creationTimestamp;

    public Comment(User user, Post post, String text) {
        this.user = user;
        this.post = post;
        this.text = text;
    }

    public Long getId() {
        return id;
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

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    protected Comment(){}
}
