package se.netwomen.NetWomenBackend.repository.DTO.dto.Post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class PostDTO {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private UserDTO user;
    private String pictureUrl;
    private String text;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    private LocalDateTime date;
    private String postNumber;

    protected PostDTO() {
    }

    public PostDTO(UserDTO user, String pictureUrl, String text, LocalDateTime creationTimestamp, String postNumber) {
        this.user = user;
        this.pictureUrl = pictureUrl;
        this.text = text;
        this.date = creationTimestamp;
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

    public LocalDateTime getDate() {
        return date;
    }

    public PostDTO setUserDTO(UserDTO userDTO) {
        this.user = userDTO;
        return this;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
