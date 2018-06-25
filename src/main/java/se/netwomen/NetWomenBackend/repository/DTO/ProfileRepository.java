package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;

import java.util.Optional;

@Repository("profileDTO")
public interface ProfileRepository extends CrudRepository<ProfileDTO, Long> {

    Optional<ProfileDTO> findByProfileNumber(String profileNumber);

    /*Test att hitta användare med UserId, funkar ej än
     @Query("SELECT p FROM #{#entityName} p INNER JOIN p.user u WHERE u.id = :id")
     Optional<ProfileTest> findProfileTestByUserId(@Param("id") Long id);*/

}
