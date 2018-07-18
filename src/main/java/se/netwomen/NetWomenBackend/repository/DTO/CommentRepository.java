package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.CommentDTO;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentDTO, Long> {

    @Query("select c from CommentDTO c where c.post.id = ?1")
    List<CommentDTO> getCommentsByPostId(Long id);

}
