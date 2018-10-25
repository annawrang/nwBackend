package se.netwomen.NetWomenBackend.repository.DTO.dto.User.user;

<<<<<<< HEAD:src/main/java/se/netwomen/NetWomenBackend/repository/DTO/dto/Network/repository/UserRepository.java
import org.springframework.data.jpa.repository.EntityGraph;
=======
>>>>>>> master:src/main/java/se/netwomen/NetWomenBackend/repository/DTO/dto/User/user/UserRepository.java
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Long>{

    Optional<UserDTO> findByEmailAndPassword(String email, String password);

    Optional<UserDTO> findByUserNumber(String userNumber);

    Optional<UserDTO> findByEmail(String email);

    @EntityGraph(value ="UserDTO.networkDTOs")
    Optional<UserDTO> findOneWithNetworkDTOsByUserNumber(String userNumber);

}
