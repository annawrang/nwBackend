package se.netwomen.NetWomenBackend.repository.DTO.dto.Post.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentReplyDTO;

import java.util.List;
import java.util.Optional;

public interface CommentReplyRepository extends CrudRepository<CommentReplyDTO, Long> {

    @Query("select c from CommentReplyDTO c where c.headComment.commentNumber = ?1 order by c.date asc ")
    List<CommentReplyDTO> findByHeadCommentNumber(String commentNumber);


    Optional<CommentReplyDTO> findByCommentReplyNumber(String replyNumber);
}
