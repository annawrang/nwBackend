package se.netwomen.NetWomenBackend.service;

import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileMine;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileOthers;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.service.Parsers.ProfileParser;

import javax.ws.rs.BadRequestException;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;

    }

    public ProfileMine getMyProfile(String userNumber, String profileNumber) {
        if (userNumber.equals(profileNumber)) {
            return profileRepository.findByUserNumber(userNumber)
                    .map(ProfileParser::dtoToProfileMine)
                    .orElseThrow(BadRequestException::new);
        }
        throw new BadRequestException();
    }


    public ProfileOthers getOthersProfile(String profileUserNumber) {
        return profileRepository.findByUserNumber(profileUserNumber)
                .map(ProfileParser::dtoToProfileOthers)
                .orElseThrow(BadRequestException::new);
    }

    public boolean editProfile(ProfileMine profileMine) {
        ProfileDTO oldDTO = profileRepository.findByUserNumber(profileMine.getUser().getUserNumber()).get();
        UserDTO userDTO = userRepository.findByUserNumber(profileMine.getUser().getUserNumber()).get();
        ProfileDTO newProfileDTO = new ProfileDTO(profileMine.getDescription(), userDTO);
        newProfileDTO.setId(oldDTO.getId());

        newProfileDTO = profileRepository.save(newProfileDTO);
        return newProfileDTO.getId() != null;
    }
}
