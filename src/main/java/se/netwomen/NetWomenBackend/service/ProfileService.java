package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.repository.DTO.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.service.Parsers.ProfileParser;

import javax.ws.rs.BadRequestException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;


    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;

    }
    public Profile createProfile(Profile profile){
        ProfileDTO profileDTO = ProfileParser.toProfileDTO(profile);
        profileDTO = profileRepository.save(profileDTO);
        return profile;
    }

    public Profile save(String userNumber, Profile profile){
        Optional<UserDTO> userDTO= userRepository.findByUserNumber(userNumber);
        if (userDTO.isPresent()){
            profile.setProfileNumber(UUID.randomUUID().toString());
            ProfileDTO profileDTO = ProfileParser.toProfileDTO(profile);
            profileDTO.setUserDTO(userDTO.get());
            profileDTO = profileRepository.save(profileDTO);
            return profile;
        }
        else {
            throw new BadRequestException();
        }
    }
/*Funkar ej*/
    public Profile saveUser(String userNumber, Profile profile){
        Optional<UserDTO> userDTO= userRepository.findByUserNumber(userNumber);
        if (userDTO.isPresent()){
            if (profile.getProfileNumber()==null){
                profile.setProfileNumber(UUID.randomUUID().toString());
            }
            ProfileDTO profileDTO = ProfileParser.toProfileDTO(profile);
            profileDTO = profileRepository.save(profileDTO);
            return  profile;
        }
        else {
            throw new BadRequestException();
        }
    }

    public Iterable<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll();
    }
}
