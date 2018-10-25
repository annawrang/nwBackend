package se.netwomen.NetWomenBackend.service;

import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileMine;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileOthers;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.post.PostRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.profile.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.user.UserRepository;
import se.netwomen.NetWomenBackend.service.Parsers.ProfileParser;
import se.netwomen.NetWomenBackend.service.logic.PostLogic;
import se.netwomen.NetWomenBackend.service.logic.ProfileLogic;
import se.netwomen.NetWomenBackend.service.logic.UserLogic;

import javax.ws.rs.BadRequestException;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLogic postLogic;
    private final ProfileLogic profileLogic;
    private final UserLogic userLogic;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository,
                          PostRepository postRepository, PostLogic postLogic,
                          ProfileLogic profileLogic, UserLogic userLogic) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postLogic = postLogic;
        this.profileLogic = profileLogic;
        this.userLogic = userLogic;
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

    public void editProfile(ProfileMine profileMine, String profileNumber, String userNumber) {
        ProfileDTO oldDTO = profileLogic.valiadeteProfile(userNumber);
        UserDTO userDTO = userLogic.validateUserExists(userNumber);

        ProfileDTO newProfileDTO = new ProfileDTO(profileMine.getDescription(), userDTO);
        newProfileDTO.setId(oldDTO.getId());

        newProfileDTO = profileRepository.save(newProfileDTO);
    }
}
