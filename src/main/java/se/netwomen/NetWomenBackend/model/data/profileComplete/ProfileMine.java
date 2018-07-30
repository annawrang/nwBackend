package se.netwomen.NetWomenBackend.model.data.profileComplete;

import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;

public class ProfileMine {

    private String description;
    private UserMinimum user;
    private String workTitle;

    // difference between Others and Mine
    private String email;

    protected ProfileMine(){}

    public ProfileMine(String description, UserMinimum user) {
        this.description = description;
        this.user = user;
    }


    public String getDescription() {
        return description;
    }

    public UserMinimum getUser() {
        return user;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }
}
