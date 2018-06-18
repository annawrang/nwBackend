package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.model.data.UserMinimum;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.Optional;

public final class UserParser {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getFirstName(), user.getSurName(), user.getUserName(), user.getEmail(), user.getPassword());
    }

    public static User toUser(UserDTO userDTO) {
        return new User(userDTO.getFirstName(), userDTO.getSurName(), userDTO.getUserName(), userDTO.getEmail(), userDTO.getPassword());
    }

    public static UserMinimum UserDTOToUserMinimum(UserDTO user) {
        return new UserMinimum(user.getFirstName(), user.getSurName(), user.getEmail());
    }

    public static UserDTO toUseMinimumDTO(UserMinimum user){
        return new UserDTO(user.getFirstName(), user.getSurName(), user.getEmail());
    }
}
