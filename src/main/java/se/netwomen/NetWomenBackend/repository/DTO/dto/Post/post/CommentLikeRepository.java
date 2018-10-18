package se.netwomen.NetWomenBackend.repository.DTO.dto.Post.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentLikeDTO;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeDTO, Long> {

    @Query("select cl from CommentLikeDTO cl where cl.comment.commentNumber = ?1 and cl.user.userNumber = ?2")
    Optional<CommentLikeDTO> findByCommentNumberAndUserNumber(String commentNumber, String userNumber);

    @Query("select cl from CommentLikeDTO cl where cl.comment.commentNumber = ?1")
    List<CommentLikeDTO> findByCommentNumber(String commentNumber);
}
