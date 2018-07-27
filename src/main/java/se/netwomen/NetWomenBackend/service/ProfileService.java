package se.netwomen.NetWomenBackend.service;

import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileMine;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileOthers;
import se.netwomen.NetWomenBackend.repository.DTO.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.service.Parsers.ProfileParser;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;

    }

    public ProfileMine getMyProfile(String userNumber, String profileNumber) {
        if(userNumber.equals(profileNumber)){
            Optional<ProfileDTO> profileDTO = profileRepository.findByUserNumber(userNumber);
            if(profileDTO.isPresent()){
                ProfileMine myProfile = ProfileParser.dtoToProfileMine(profileDTO.get());
                return myProfile;
            }
        }
        return null;
    }

    public ProfileOthers getOthersProfile(String profileUserNumber) {
        Optional<ProfileDTO> profileDTO = profileRepository.findByUserNumber(profileUserNumber);
        if(profileDTO.isPresent()){
            ProfileOthers othersProfile = ProfileParser.dtoToProfileOthers(profileDTO.get());
            return othersProfile;
        }
        return null;
    }

    public boolean editProfile(ProfileMine profileMine) {
        ProfileDTO oldDTO = profileRepository.findByUserNumber(profileMine.getUser().getUserNumber()).get();
        UserDTO userDTO = userRepository.findByUserNumber(profileMine.getUser().getUserNumber()).get();
        ProfileDTO newProfileDTO = new ProfileDTO(profileMine.getDescription(), userDTO);
        newProfileDTO.setId(oldDTO.getId());

        newProfileDTO = profileRepository.save(newProfileDTO);
        if(newProfileDTO.getId() != null){
            return true;
        }
        return false;
    }
}
