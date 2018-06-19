package se.netwomen.NetWomenBackend.repository.DTO.dto.Post;

import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class PostDTO {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private UserDTO user;
    private String pictureUrl;
    private String text;
    private Timestamp creationTimestamp; // Måste läggas till i konstruktor etc
    private String postNumber;

    protected PostDTO(){}

    public PostDTO(UserDTO user, String pictureUrl, String text, Timestamp creationTimestamp, String postNumber) {
        this.user = user;
        this.pictureUrl = pictureUrl;
        this.text = text;
        this.creationTimestamp = creationTimestamp;
        this.postNumber = postNumber;
    }

    public Long getId() {
        return id;
    }

    public UserDTO getUser() {
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

    public void setUserDTO(UserDTO userDTO) {
        this.user = userDTO;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }
}
