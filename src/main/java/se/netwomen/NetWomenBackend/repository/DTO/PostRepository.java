package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostDTO, Long>{

    @Query("select p from PostDTO p order by p.creationTimestamp desc")
    List<PostDTO> findAllOrderedByTimeStamp();

    PostDTO findByPostNumber(String postNumber);

}
