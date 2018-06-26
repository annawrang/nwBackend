package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.repository.DTO.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostLikeDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.service.Parsers.ProfileParser;

import javax.ws.rs.BadRequestException;
import java.sql.Timestamp;
import java.util.Collection;
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

    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        profileDTO.setProfileNumber(UUID.randomUUID().toString());
        return profileRepository.save(profileDTO);
    }
    /*Funkar ej än*/
    /*
    public Profile createProfile2(Profile profile) {
        profile.setProfileNumber(UUID.randomUUID().toString());
        ProfileDTO profileDTO = ProfileParser.toProfileDTO(profile);
        profileDTO = profileRepository.save(profileDTO);
        return profile;
    }*/

    /*funkar ej */
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
    public ProfileDTO getProfileByProfileNumber(String profileNumber){
        Optional<ProfileDTO> profileDT = profileRepository.findByProfileNumber(profileNumber);
        if (profileDT.isPresent()){
            return profileDT.get();
        }
        throw new BadRequestException();
    }

    public Collection<ProfileDTO> getProfileForUser(String userNumber){
        Optional<UserDTO> userDTOOptional = userRepository.findByUserNumber(userNumber);
        if (userDTOOptional.isPresent()){
            return profileRepository.findProfileDTOByUserId(userDTOOptional.get().getUserNumber());
        }
        throw new BadRequestException();
    }

    public Iterable<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll();
    }
}
