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

    protected ProfileDTO(){}

    public ProfileDTO(UserDTO user, String description) {
        this.description = description;
        this.user = user;
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
}
