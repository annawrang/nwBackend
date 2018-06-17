package se.netwomen.NetWomenBackend.model.data;

public final class User {

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
    //Konstruktor som kommer att användas i Profile sidan
    public User(String firstName, String surName, String email){
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
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

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
