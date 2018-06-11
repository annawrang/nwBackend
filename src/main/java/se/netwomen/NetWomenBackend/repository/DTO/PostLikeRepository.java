package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.model.data.PostLike;

import java.util.List;

@Repository
public interface PostLikeRepository extends CrudRepository<PostLike, Long> {

    @Query("select pl from PostLike pl where pl.post.id = ?1")
    List<PostLike> findAllByPostId(Long id);

    @Query("select count(pl) from PostLike pl where pl.post.id = ?1")
    int countPostLikesByPostId(Long id);
}
