package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;

import java.util.Collection;
import java.util.Optional;

@Repository("profileDTO")
public interface ProfileRepository extends CrudRepository<ProfileDTO, Long> {

    Optional<ProfileDTO> findByProfileNumber(String profileNumber);

     @Query("SELECT p FROM #{#entityName} p INNER JOIN p.user u WHERE u.userNumber = :userNumber")
     Collection<ProfileDTO> findProfileDTOByUserId(@Param("userNumber") String userNumber);
}
