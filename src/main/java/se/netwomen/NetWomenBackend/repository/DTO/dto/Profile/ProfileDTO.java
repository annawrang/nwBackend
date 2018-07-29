package se.netwomen.NetWomenBackend.repository.DTO.dto.Profile;

import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;

@Entity
public class ProfileDTO {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @OneToOne
    @JoinColumn(unique = true)
    private UserDTO user;

    protected ProfileDTO(){}

    public ProfileDTO(String description, UserDTO user) {
        this.description = description;
        this.user = user;
    }

    public ProfileDTO(UserDTO user){
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

    public void setId(Long id) {
        this.id = id;
    }
}
