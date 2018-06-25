package se.netwomen.NetWomenBackend.model.data;

public final class User {

    private String firstName;
    private String surName;
    private String userNumber;
    private String email;
    private String password;
    private String cookie;

    protected User(){}

    public User(String firstName, String surName, String email, String password) {
        this.firstName = firstName;
        this.surName = surName;
        this.userNumber = userNumber;
        this.email = email;
        this.password = password;
    }
    //Konstruktor som kommer att användas i Profile sidan
    public User(String firstName, String surName, String email){
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
    }

    public User(String firstName, String surName, String userNumber, String email, String password) {
        this.firstName = firstName;
        this.surName = surName;
        this.userNumber = userNumber;
        this.email = email;
        this.password = password;
    }

    public String getUserNumber() {
        return userNumber;
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

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
