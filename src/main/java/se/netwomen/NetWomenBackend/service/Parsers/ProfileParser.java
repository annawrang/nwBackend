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
    /* CLAUDIA KOLLA PÅ DETTA
     public static Post postDTOToPost(PostDTO postDTO) {
        return new Post(UserParser.toUser(postDTO.getUser()), postDTO.getText(), postDTO.getPictureUrl(), postDTO.getCreationTimestamp());
    }
    public static Post profileDTOToProfile(PostDTO postDTO) {
        return new Post(UserParser.toUser(postDTO.getUser()), postDTO.getText(), postDTO.getPictureUrl(), postDTO.getCreationTimestamp());
    }
    */
}
