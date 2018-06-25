package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.resource.security.JwtTokenProvider;
import se.netwomen.NetWomenBackend.service.Parsers.UserParser;
import se.netwomen.NetWomenBackend.service.exceptions.CustomException;

import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        if (user.getUserNumber() == null) {
            user.setUserNumber(UUID.randomUUID().toString());
        }
        UserDTO userDTO = UserParser.toUserDTO(user);
        userDTO = repository.save(userDTO);
        return user;
    }

    public User findByEmailAndPassword(String email, String password) {
        Optional<UserDTO> userDTO = repository.findByEmailAndPassword(email, password);
        if (userDTO.isPresent()) {
            return UserParser.toUser(userDTO.get());
        }
        return null;
    }

    public void setCookie(String userName, String password, String cookie) {
        Optional<UserDTO> optionalUser = repository.findByEmailAndPassword(userName, password);
        if (optionalUser.isPresent()) {
            optionalUser.get().setCookie(cookie);
            repository.save(optionalUser.get());
        }
    }

    public String signIn(String email, String password) {
        try {
            System.out.println("rad 66");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            System.out.println("rad 68");
            return jwtTokenProvider.createToken(email, repository.findByEmailAndPassword(email, password).get().getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
