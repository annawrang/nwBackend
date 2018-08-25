package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Profile;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileMine;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileOthers;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

public final class ProfileParser {

    public static ProfileMine dtoToProfileMine(ProfileDTO profileDTO) {
        return new ProfileMine(profileDTO.getDescription(), UserParser.toUserMinimum(profileDTO.getUser()));
    }

    public static ProfileOthers dtoToProfileOthers(ProfileDTO profileDTO) {
        return new ProfileOthers(profileDTO.getDescription(), UserParser.toUserMinimum(profileDTO.getUser()));
    }



}
