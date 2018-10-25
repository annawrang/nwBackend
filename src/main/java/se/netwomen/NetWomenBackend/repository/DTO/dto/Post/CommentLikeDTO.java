package se.netwomen.NetWomenBackend.repository.DTO.dto.Post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class CommentLikeDTO  {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private UserDTO user;
    @ManyToOne
    private CommentDTO comment;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime date;
    private String commentLikeNumber;

    protected CommentLikeDTO() {
    }

    public CommentLikeDTO(UserDTO user, CommentDTO comment, LocalDateTime date, String commentLikeNumber) {
        this.user = user;
        this.comment = comment;
        this.date = date;
        this.commentLikeNumber = commentLikeNumber;
    }

    public Long getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public CommentDTO getComment() {
        return comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCommentLikeNumber() {
        return commentLikeNumber;
    }
}
