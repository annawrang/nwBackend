package se.netwomen.NetWomenBackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileMine;
import se.netwomen.NetWomenBackend.model.data.profileComplete.ProfileOthers;
import se.netwomen.NetWomenBackend.service.ProfileService;
import se.netwomen.NetWomenBackend.service.exceptions.EmailNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

    private final ProfileService profileService;
    @Context
    private UriInfo uriInfo;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @PutMapping(path = "{profileNumber}")
    public ResponseEntity editProfile(@PathVariable String profileNumber, @RequestBody ProfileMine profileMine) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());

        profileService.editProfile(profileMine, profileNumber, userNumber);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "{profileNumber}")
    public ResponseEntity getProfile(@PathVariable String profileNumber) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());

        if (isSameUser(userNumber, profileNumber)) {
            ProfileMine profile = profileService.getMyProfile(userNumber, profileNumber);
            return new ResponseEntity(profile, HttpStatus.OK);
        } else {
            ProfileOthers profile = profileService.getOthersProfile(profileNumber);
            return new ResponseEntity(profile, HttpStatus.OK);
        }
    }

    private boolean isSameUser(String userNumber, String profileUserNumber) {
        if (userNumber.equals(profileUserNumber)) {
            return true;
        }
        return false;
    }

    // This is the method use to get the usernumber from the token, to always know which user is
    // sending the request
    private String getUserNumberFromAuth(String auth) {
        if (auth == null) {
            throw new EmailNotFoundException("Usernumber not found.");
        }
        String userNumber = auth.split(";")[0];
        userNumber = userNumber.substring(userNumber.lastIndexOf(":") + 2).trim();
        if (userNumber == null) {
            throw new BadRequestException();
        }
        return userNumber;
    }
}
