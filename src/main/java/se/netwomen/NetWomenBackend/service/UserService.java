package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.profile.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.user.UserRepository;
import se.netwomen.NetWomenBackend.controller.security.JwtTokenProvider;
import se.netwomen.NetWomenBackend.service.Parsers.UserParser;
import se.netwomen.NetWomenBackend.service.exceptions.CustomException;
import se.netwomen.NetWomenBackend.service.logic.UserLogic;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserLogic userLogic;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository repository, ProfileRepository profileRepository, UserLogic userLogic) {
        this.userRepository = repository;
        this.profileRepository = profileRepository;
        this.userLogic = userLogic;
    }

    public boolean saveNewUser(User user) {
        this.userLogic.validateEmailDoesntExists(user.getEmail());
        user.setUserNumber(UUID.randomUUID().toString());
        UserDTO userDTO = userRepository.save(UserParser.toUserDTO(user));
        return createNewProfileForUser(userDTO);
    }

    private boolean createNewProfileForUser(UserDTO userDTO) {
        ProfileDTO profile = new ProfileDTO(userDTO);
        profile = profileRepository.save(profile);
        return (profile.getId() != null);
    }

    public User findByEmailAndPassword(String email, String password) {
        return UserParser.toUser(userLogic.validateUserByEmailPassword(email, password));
    }


    public String signIn(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email, userRepository.findByEmailAndPassword(email, password).get().getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
