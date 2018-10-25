package se.netwomen.NetWomenBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.service.ProfileService;
import se.netwomen.NetWomenBackend.service.UserService;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final ProfileService profileService;

    @Autowired
    public UserController(UserService userService, ProfileService profileService) {
        this.service = userService;
        this.profileService = profileService;
    }


    // Authenticates user when they login, and sends a JwtToken
    @PostMapping(path = "authenticate")
    public ResponseEntity signIn(@RequestParam("email") String email,
                           @RequestParam("password") String password) {
        String jwtToken = this.service.signIn(email, password);
        User user = service.findByEmailAndPassword(email, password);
        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Auth-Token", jwtToken);
            headers.add("Usernumber", user.getUserNumber());

            return ResponseEntity.ok()
                    .headers(headers).build();
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // Creates a new user
    @PostMapping(path = "signup")
    public ResponseEntity createNewUser(@RequestBody User user) {
        if (service.saveNewUser(user)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
