package se.netwomen.NetWomenBackend.repository.DTO.dto.Post.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentReplyLikeDTO;

import java.util.Optional;

public interface CommentReplyLikeRepository extends CrudRepository<CommentReplyLikeDTO, Long> {

    Optional<CommentReplyLikeDTO> findByCommentReplyNumber(String commentReplyNumber);

    @Query("select c from CommentReplyLikeDTO c where c.user.userNumber = ?1 and c.commentReplyNumber = ?2")
    Optional<CommentReplyLikeDTO> findByCommentReplyNumberAndUserNumber(String userNumber,String commentReplyNumber);

}
