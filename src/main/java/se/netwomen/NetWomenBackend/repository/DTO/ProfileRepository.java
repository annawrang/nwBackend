package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.model.data.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
    //Find profile by firstName?
    //Profile findByFirstName(String firstName);
    //Optional<Profile> findProfileByUser_FirstName(String firstName);
}
