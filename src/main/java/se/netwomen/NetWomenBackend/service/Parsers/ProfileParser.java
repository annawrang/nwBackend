package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;

public final class ProfileParser {

    public static ProfileDTO toProfileDTO(Profile profile) {
        return new ProfileDTO(profile.getDescription(), UserParser.toUserDTO(profile.getUser()));
    }

    public static Profile toProfile(ProfileDTO profileDTO) {
        return new Profile(profileDTO.getDescription(), UserParser.toUser(profileDTO.getUser()));
    }
}
