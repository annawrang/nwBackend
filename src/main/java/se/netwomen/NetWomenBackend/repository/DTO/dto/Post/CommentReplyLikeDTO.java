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
public class CommentReplyLikeDTO {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private CommentReplyDTO reply;
    @ManyToOne
    private UserDTO user;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime date;
    private String commentReplyNumber;

    protected CommentReplyLikeDTO(){}

    public CommentReplyLikeDTO(CommentReplyDTO reply, UserDTO user, LocalDateTime date, String commentReplyNumber) {
        this.reply = reply;
        this.user = user;
        this.date = date;
        this.commentReplyNumber = commentReplyNumber;
    }

    public Long getId() {
        return id;
    }

    public CommentReplyDTO getReply() {
        return reply;
    }

    public UserDTO getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCommentReplyNumber() {
        return commentReplyNumber;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCommentReplyNumber(String commentReplyNumber) {
        this.commentReplyNumber = commentReplyNumber;
    }
}
