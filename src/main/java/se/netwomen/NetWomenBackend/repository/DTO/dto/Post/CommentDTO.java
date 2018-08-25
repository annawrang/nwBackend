package se.netwomen.NetWomenBackend.repository.DTO.dto.Post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    private LocalDateTime date;
    private String commentNumber;

    public CommentDTO(UserDTO user, PostDTO post, String text, LocalDateTime date, String commentNumber) {
        this.user = user;
        this.post = post;
        this.text = text;
        this.date = date;
        this.commentNumber = commentNumber;
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

    public LocalDateTime getDate() {
        return date;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    protected CommentDTO(){}
}
