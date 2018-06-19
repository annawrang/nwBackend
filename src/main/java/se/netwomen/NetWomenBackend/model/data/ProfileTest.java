package se.netwomen.NetWomenBackend.model.data;

import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;

@Entity
public class ProfileTest {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @OneToOne
    @JoinColumn(unique = true)
    private UserDTO user;

    protected ProfileTest(){}

    public ProfileTest(String description, UserDTO user) {
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


