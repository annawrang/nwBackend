package se.netwomen.NetWomenBackend.repository.DTO.dto.Post.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<PostDTO, Long>{

    // ANNA - gör så att den bara returnerar 10 st
    @Query("select p from PostDTO p order by p.date desc")
    List<PostDTO> findAllOrderedByDate();


    Optional<PostDTO> findByPostNumber(String postNumber);

    @Query("select p from PostDTO p where p.user.userNumber = ?1 order by p.date desc")
    List<PostDTO> findByUserNumber(String userNumber, int page, int numbersPerPage);

}
