package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.repository.DTO.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.service.Parsers.ProfileParser;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;


    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;

    }
    /*public ProfileTest createP(ProfileTest profileTest){
        return profileRepository.save(profileTest);
    }*/

    public Profile createProfile(Profile profile){
        ProfileDTO profileDTO = ProfileParser.toProfileDTO(profile);
        profileDTO = profileRepository.save(profileDTO);
        return profile;
    }

    public Iterable<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll();
    }

    /*Funkar ej än!!
    public ProfileTest findByUserId(Long id){
        Optional<ProfileTest> profileOptional = profileRepository.findProfileTestByUserId(id);
        return profileOptional.get();
    }*/
}
