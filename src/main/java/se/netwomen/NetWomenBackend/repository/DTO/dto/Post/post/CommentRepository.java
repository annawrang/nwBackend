package se.netwomen.NetWomenBackend.repository.DTO.dto.Post.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentDTO, Long> {

    @Query("select c from CommentDTO c where c.post.id = ?1")
    List<CommentDTO> getCommentsByPostId(Long id);

    @Query("select c from CommentDTO c where c.commentNumber = ?1")
    Optional<CommentDTO> findByCommentNumber(String commentNumber);
}
