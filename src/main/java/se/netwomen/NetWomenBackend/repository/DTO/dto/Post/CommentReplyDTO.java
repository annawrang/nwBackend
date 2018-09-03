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
public class CommentReplyDTO {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private CommentDTO headComment;
    @ManyToOne
    private UserDTO user;
    private String text;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime date;
    private String commentReplyNumber;

    protected CommentReplyDTO(){}

    public CommentReplyDTO(CommentDTO headComment, UserDTO user, String text, LocalDateTime date, String commentReplyNumber) {
        this.headComment = headComment;
        this.user = user;
        this.text = text;
        this.date = date;
        this.commentReplyNumber = commentReplyNumber;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public CommentDTO getHeadComment() {
        return headComment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getCommentReplyNumber() {
        return commentReplyNumber;
    }
}
