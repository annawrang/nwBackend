package se.netwomen.NetWomenBackend.service;

import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.model.data.ProfileTest;
import se.netwomen.NetWomenBackend.repository.DTO.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.service.Parsers.ProfileParser;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

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

    /*public Profile createProfile (Profile profile){
        ProfileDTO profileDTO = ProfileParser.toProfileDTO(profile);
        profileDTO = profileRepository.save(profileDTO);
        return profile;
    }
     public User save(User user) {
        UserDTO userDTO = UserParser.toUserDTO(user);
        userDTO =  repository.save(userDTO);
        return user;
    }

    */
    /*Funkar ej än!!
    public ProfileTest findByUserId(Long id){
        Optional<ProfileTest> profileOptional = profileRepository.findProfileTestByUserId(id);
        return profileOptional.get();
    }*/
}
