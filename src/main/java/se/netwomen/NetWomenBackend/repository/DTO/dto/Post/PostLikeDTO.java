package se.netwomen.NetWomenBackend.repository.DTO.dto.Post;

import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class PostLikeDTO {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private UserDTO user;
    @ManyToOne
    private PostDTO post;
    private Timestamp timestamp;

    public PostLikeDTO(UserDTO user, PostDTO post, Timestamp creationTimestamp) {
        this.user = user;
        this.post = post;
        this.timestamp = creationTimestamp;
    }

    protected PostLikeDTO(){}

    public Long getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public PostDTO getPost() {
        return post;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
