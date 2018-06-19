package se.netwomen.NetWomenBackend.service;

import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.ProfileTest;
import se.netwomen.NetWomenBackend.repository.DTO.ProfileRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;

    }
    public ProfileTest createP(ProfileTest profileTest){
        return profileRepository.save(profileTest);
    }
    /*public Profile createProfile (Profile profile){
        ProfileDTO profileDTO = ProfileParser.toProfileDTO(profile);
        profileDTO = profileRepository.save(profileDTO);
        return profile;
    }*/
    /*Funkar ej än!!*/
    public ProfileTest findByUserId(Long id){
        Optional<ProfileTest> profileOptional = profileRepository.findProfileTestByUserId(id);
        return profileOptional.get();
    }
}
