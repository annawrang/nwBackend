package se.netwomen.NetWomenBackend.repository.DTO.dto.Profile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;

@Entity
public class ProfileDTO {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JsonBackReference
    @JoinColumn(unique = true)
    private UserDTO user;
    private String description;
    private String profileNumber;

    protected ProfileDTO(){}

    public ProfileDTO(UserDTO user, String description, String profileNumber) {
       /* this.user= user;
        this.description=description;
        this.profileNumber=profileNumber;*/
    }

    public ProfileDTO(UserDTO user, ProfileDTO profileDTo) {
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
