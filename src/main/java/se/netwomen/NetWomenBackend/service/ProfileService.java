package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.repository.DTO.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;

    }
    public Profile createProfile (Profile profile){
        return profileRepository.save(profile);
    }

    /*public Optional<Profile> findByUserFirstName(String firstName){
        return profileRepository.findProfileByUser_FirstName(firstName);
    }*/
}
