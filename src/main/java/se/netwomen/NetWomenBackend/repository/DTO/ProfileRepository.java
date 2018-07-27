package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileDTO, Long> {

    @Query("select p from ProfileDTO p where p.user.userNumber = ?1")
    Optional<ProfileDTO> findByUserNumber(String userNumber);
}
