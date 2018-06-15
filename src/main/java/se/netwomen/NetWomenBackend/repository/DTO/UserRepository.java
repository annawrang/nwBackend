package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Long>{
    Optional<UserDTO> findByUserNameAndPassword(String userName, String password);

    Optional<UserDTO> findByUserName(String userName);

    @Query("select u from UserDTO u where u.cookie = ?1")
    Optional<UserDTO> findUsersCookie(String cookie);
}
