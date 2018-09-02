package se.netwomen.NetWomenBackend.repository.DTO.dto.User;

import se.netwomen.NetWomenBackend.model.data.Role;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NamedEntityGraph(name = "UserDTO.networkDTOs", attributeNodes = @NamedAttributeNode("networkDTOs"))
public class UserDTO {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String surName;
    private String email;
    private String userNumber;
    private String password;
    private String workTitle;
    @ManyToMany
    private Set <NetworkDTO> networkDTOs;

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

    public String getEmail() {
        return email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public Set<NetworkDTO> getNetworkDTOs() {
        return networkDTOs;
    }
    public void addNetworkDTO(NetworkDTO networkDTO){
        networkDTOs.add(networkDTO);
    }
}
