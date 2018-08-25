package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<PostDTO, Long>{

    @Query("select p from PostDTO p order by p.date desc")
    List<PostDTO> findAllOrderedByDate();

    Optional<PostDTO> findByPostNumber(String postNumber);

}
