package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Long>{

    Optional<UserDTO> findByEmailAndPassword(String email, String password);

    Optional<UserDTO> findByUserNumber(String userNumber);

    Optional<UserDTO> findByEmail(String email);

    @EntityGraph(value ="UserDTO.networkDTOs")
    Optional<UserDTO> findOneWithNetworkDTOsByUserNumber(String userNumber);

}
