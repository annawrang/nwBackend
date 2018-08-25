package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostLike;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Long>{

    Optional<UserDTO> findByEmailAndPassword(String email, String password);

    Optional<UserDTO> findByUserNumber(String userNumber);

    Optional<UserDTO> findByEmail(String email);

}
