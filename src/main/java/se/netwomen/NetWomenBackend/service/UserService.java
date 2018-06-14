package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.service.Parsers.UserParser;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User save(User user) {
        UserDTO userDTO = UserParser.toUserDTO(user);
        userDTO =  repository.save(userDTO);
        return user;
    }

    public User findByUserNameAndPassword(String userName, String password) {
        Optional<UserDTO> userDTO = repository.findByUserNameAndPassword(userName, password);
        if(userDTO.isPresent()){
            return UserParser.toUser(userDTO.get());
        }
        return null;
    }

    public void setCookie(String userName, String password, String cookie) {
        Optional<UserDTO> optionalUser = repository.findByUserNameAndPassword(userName, password);
        if (optionalUser.isPresent()){
            optionalUser.get().setCookie(cookie);
            repository.save(optionalUser.get());
        }
    }
}
