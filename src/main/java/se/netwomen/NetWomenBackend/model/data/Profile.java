package se.netwomen.NetWomenBackend.model.data;

import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;

public final class Profile {

    private String mission;
    private String areaExpertise;
    private String profilePicLink;
    private String backgroundPicLink;
    private String profileNumber;
    private UserMinimum user;

    protected Profile() {
    }

    public Profile(String description, UserMinimum user) {
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

    public String getAreaExpertise() {
        return areaExpertise;
    }

    public void setAreaExpertise(String areaExpertise) {
        this.areaExpertise = areaExpertise;
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

    public void setUser(UserMinimum user) {
        this.user = user;
    }


    public String getMission() {
        return mission;
    }

    public UserMinimum getUser() {
        return user;
    }
}
