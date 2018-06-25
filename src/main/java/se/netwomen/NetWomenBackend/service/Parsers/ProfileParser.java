package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

public final class ProfileParser {

    public static ProfileDTO toProfileDTO(Profile profile) {
        return new ProfileDTO(new UserDTO("", "", ""), profile.getDescription(), profile.getProfileNumber());
    }
    public static Profile toProfile(ProfileDTO profileDTO) {
        return new Profile(UserParser.toUserMinimum(profileDTO.getUser()), profileDTO.getDescription(), profileDTO.getProfileNumber());
    }
}
