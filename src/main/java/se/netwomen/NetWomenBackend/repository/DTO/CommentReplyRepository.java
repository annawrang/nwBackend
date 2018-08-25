package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentReplyDTO;

import java.util.List;

public interface CommentReplyRepository extends CrudRepository<CommentReplyDTO, Long> {


    @Query("select c from CommentReplyDTO c where c.headComment.commentNumber = ?1")
    List<CommentReplyDTO> findByHeadCommentNumber(String commentNumber);

//    @Query("select c from CommentReplyDTO c where c.commentReplyNumber = ?1")
//    Optional<CommentReplyDTO> findByCommentReplyNumber(String commentReplyNumber);
}
