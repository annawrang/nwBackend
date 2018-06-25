package se.netwomen.NetWomenBackend.repository.DTO.dto.Profile;

import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;

@Entity
public class ProfileDTO {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(unique = true)
    private UserDTO user;
    private String description;
    private String profileNumber;

    protected ProfileDTO(){}

    public ProfileDTO(UserDTO user, String description, String profileNumber) {
        this.description = description;
        this.user = user;
        this.profileNumber=profileNumber;
    }

    public String getProfileNumber() {
        return profileNumber;
    }

    public void setProfileNumber(String profileNumber) {
        this.profileNumber = profileNumber;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.user = userDTO;
    }
}
