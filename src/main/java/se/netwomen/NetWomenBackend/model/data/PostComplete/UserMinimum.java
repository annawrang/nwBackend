package se.netwomen.NetWomenBackend.model.data.PostComplete;

/*  This class is what is sent back when we need a user that's connected
* to a post, comment, like etc. Where we don't want all the information */

public final class UserMinimum {

    private String firstName;
    private String surName;
    private String userNumber;

    public UserMinimum(String firstName, String surName, String userNumber) {
        this.firstName = firstName;
        this.surName = surName;
        this.userNumber = userNumber;
    }

    protected UserMinimum(){}

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public String getUserNumber() {
        return userNumber;
    }
}
