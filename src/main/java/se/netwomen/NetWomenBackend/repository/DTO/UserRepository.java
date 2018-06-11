package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.model.data.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUserNameAndPassword(String userName, String password);

    User findByUserName(String userName);

    @Query("select count(u.cookie) from User u where u.cookie = ?1")
    int findUsersCookie(String cookie);
}
