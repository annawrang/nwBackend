package se.netwomen.NetWomenBackend.model.data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class PostLike {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
    private Timestamp timestamp; // Måste läggas till & sättas i konstruktor

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    protected PostLike(){}

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
