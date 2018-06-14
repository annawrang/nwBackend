package se.netwomen.NetWomenBackend.repository.DTO.dto.Post;

import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.model.data.UserMinimum;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class CommentDTO {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private UserDTO user;
    @ManyToOne
    private PostDTO post;
    private String text;
    private Timestamp creationTimestamp;

    public CommentDTO(UserDTO user, PostDTO post, String text, Timestamp creationTimestamp) {
        this.user = user;
        this.post = post;
        this.text = text;
        this.creationTimestamp = creationTimestamp;
    }

    public Long getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public PostDTO getPost() {
        return post;
    }

    public String getText() {
        return text;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    protected CommentDTO(){}
}
