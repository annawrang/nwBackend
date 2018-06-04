package se.netwomen.NetWomenBackend.model.data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    private String pictureUrl;
    private String text;
    private Timestamp creationTimestamp; // Måste läggas till i konstruktor etc

    protected Post(){}

    public Post(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public Post(User user, String pictureUrl, String text) {
        this.user = user;
        this.pictureUrl = pictureUrl;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getText() {
        return text;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }
}
