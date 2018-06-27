package se.netwomen.NetWomenBackend.repository.DTO.dto.User;

import se.netwomen.NetWomenBackend.model.data.Role;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserDTO {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String surName;
    private String email;
    private String userNumber;
    private String password;
    private String cookie;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    protected UserDTO() {
    }

    public UserDTO(String firstName, String surName, String email, String userNumber, String password) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.userNumber = userNumber;
        this.password = password;
    }

    public UserDTO(String firstName, String surName, String email) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getCookie() {
        return cookie;
    }

    public String getEmail() {
        return email;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
