package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostLikeDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends CrudRepository<PostLikeDTO, Long> {

    @Query("select pl from PostLikeDTO pl where pl.post.id = ?1")
    List<PostLikeDTO> findAllByPostId(Long id);

    @Query("select count(pl) from PostLikeDTO pl where pl.post.id = ?1")
    int countPostLikesByPostId(Long id);

    @Query("select  pl from PostLikeDTO pl where pl.post.postNumber = ?1 and pl.user.userNumber = ?2")
    Optional<PostLikeDTO> findByPostNumberAndUserNumber(String postNumber, String userNumber);
}