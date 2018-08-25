package se.netwomen.NetWomenBackend.repository.DTO.dto.Post;

import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class PostLikeDTO {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private UserDTO user;
    @ManyToOne
    private PostDTO post;
    private LocalDateTime date;

    public PostLikeDTO(UserDTO user, PostDTO post, LocalDateTime date) {
        this.user = user;
        this.post = post;
        this.date = date;
    }

    public PostLikeDTO(UserDTO user, PostDTO post) {
        this.user = user;
        this.post = post;
        this.date = LocalDateTime.now();
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

    public LocalDateTime getDate() {
        return date;
    }
}
