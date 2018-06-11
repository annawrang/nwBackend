package se.netwomen.NetWomenBackend.model.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.ws.rs.core.NewCookie;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String surName;
    private String userName;
    private String email;
    private String password;
    private String cookie;

    protected User(){}

    public User(String firstName, String surName, String userName, String email, String password) {
        this.firstName = firstName;
        this.surName = surName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(NewCookie cookie) {
        this.cookie = cookie.toString();
    }
}
