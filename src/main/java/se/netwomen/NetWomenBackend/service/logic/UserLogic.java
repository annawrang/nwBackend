package se.netwomen.NetWomenBackend.service.logic;

import org.springframework.stereotype.Component;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.user.UserRepository;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

@Component
public class UserLogic {

    private final UserRepository userRepository;

    public UserLogic(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateEmailDoesntExists(String email) {
        userRepository.findByEmail(email)
                .ifPresent(e -> new BadRequestException("Email address is already registered."));
    }

    public UserDTO validateUserByEmailPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(NotFoundException::new);
    }

    public UserDTO validateUserExists(String userNumber) {
        return userRepository.findByUserNumber(userNumber)
                .orElseThrow(NotFoundException::new);
    }
}
