package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Long>{

    Optional<UserDTO> findByEmailAndPassword(String email, String password);

    @Query("select u from UserDTO u where u.cookie = ?1")
    Optional<UserDTO> findByCookie(String cookie);

    Optional<UserDTO> findByUserNumber(String userNumber);

    Optional<UserDTO> findByEmail(String email);

}
