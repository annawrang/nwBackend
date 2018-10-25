package se.netwomen.NetWomenBackend.repository.DTO.dto.Profile;

import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;

@Entity
public class ProfileDTO {

    @Id
    @GeneratedValue
    private Long id;
    private String mission;
    private String areasExpertise;
    private String profilePicLink;
    private String backgroundPicLink;
    private String profileNumber;
    @OneToOne
    @JoinColumn(unique = true)
    private UserDTO user;

    protected ProfileDTO() {
    }

    public ProfileDTO(String description, UserDTO user) {
        this.mission = description;
        this.user = user;
    }

    public String getProfileNumber() {
        return profileNumber;
    }

    public void setProfileNumber(String profileNumber) {
        this.profileNumber = profileNumber;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getAreasExpertise() {
        return areasExpertise;
    }

    public void setAreasExpertise(String areasExpertise) {
        this.areasExpertise = areasExpertise;
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }

    public void setProfilePicLink(String profilePicLink) {
        this.profilePicLink = profilePicLink;
    }

    public String getBackgroundPicLink() {
        return backgroundPicLink;
    }

    public void setBackgroundPicLink(String backgroundPicLink) {
        this.backgroundPicLink = backgroundPicLink;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ProfileDTO(UserDTO user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getMission() {
        return mission;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
