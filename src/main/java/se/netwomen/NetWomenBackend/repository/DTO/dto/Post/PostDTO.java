package se.netwomen.NetWomenBackend.repository.DTO.dto.Post;

import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.model.data.UserMinimum;
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

    protected PostDTO(){}

    public PostDTO(UserDTO user, String pictureUrl, String text, Timestamp creationTimestamp) {
        this.user = user;
        this.pictureUrl = pictureUrl;
        this.text = text;
        this.creationTimestamp = creationTimestamp;
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
}
