package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.model.data.Post;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{

    @Query("select p from Post p order by p.creationTimestamp desc")
    List<Post> findAllOrderedByTimeStamp();
}
