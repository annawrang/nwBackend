package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

public final class ProfileParser {

    public static ProfileDTO toProfileDTO(Profile profile) {
        return new ProfileDTO(new UserDTO("", "", ""), profile.getDescription(), profile.getProfileNumber());
    }
    /*public static PostDTO postToPostDTO(Post post) {
        return new PostDTO(new UserDTO("","",""), post.getPictureUrl(), post.getText(), post.getCreationTimestamp(), post.getPostNumber());
    }
    UserParser.toUseMinimumDTO(profile.getUser())*/
    public static Profile toProfile(ProfileDTO profileDTO) {
        return new Profile(UserParser.UserDTOToUserMinimum(profileDTO.getUser()), profileDTO.getDescription());
    }
}
