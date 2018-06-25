package se.netwomen.NetWomenBackend.model.data;

import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;

public final class Profile {

    private Long id;
    private String description;
    private UserMinimum userMinimum;
    private String profileNumber;

    protected Profile(){}

    public Profile(UserMinimum userMinimum, String description) {
        this.userMinimum = userMinimum;
        this.description = description;
    }
    public Profile(String description){
        this.description = description;
    }
    public Profile(UserMinimum userMinimum, String description, String profileNumber ) {
        this.userMinimum = userMinimum;
        this.description = description;
        this.profileNumber=profileNumber;
    }

    public String getProfileNumber() {
        return profileNumber;
    }

    public void setProfileNumber(String profileNumber) {
        this.profileNumber = profileNumber;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public UserMinimum getUser() {
        return userMinimum;
    }
}
