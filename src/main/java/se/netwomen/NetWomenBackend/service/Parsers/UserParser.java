package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

public final class UserParser {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getFirstName(), user.getSurName(), user.getEmail(), user.getUserNumber(), user.getPassword());
    }

    public static User toUser(UserDTO userDTO) {
        return new User(userDTO.getFirstName(), userDTO.getSurName(), userDTO.getUserNumber(), userDTO.getEmail(), userDTO.getPassword());
    }

    public static UserMinimum toUserMinimum(UserDTO user) {
        return new UserMinimum(user.getFirstName(), user.getSurName(), user.getUserNumber());
    }

    public static UserDTO toUseMinimumDTO(UserMinimum user){
        return new UserDTO(user.getFirstName(), user.getSurName(), user.getEmail());
    }
}
