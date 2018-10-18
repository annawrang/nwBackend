package se.netwomen.NetWomenBackend.service.logic;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.profile.ProfileRepository;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Component
public class ProfileLogic {

    private final ProfileRepository profileRepository;

    public ProfileLogic(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    @Context
    private UriInfo uriInfo;


    public ProfileDTO valiadeteProfile(String userNumber) {
        return profileRepository.findByUserNumber(userNumber).orElseThrow(NotFoundException::new);
    }
}
