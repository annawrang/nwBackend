package se.netwomen.NetWomenBackend.model.data.profileComplete;

import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;

public class ProfileOthers {

    private String description;
    private UserMinimum user;

    protected ProfileOthers() {
    }

    public ProfileOthers(String description, UserMinimum user) {
        this.description = description;
        this.user = user;
    }


    public String getDescription() {
        return description;
    }

    public UserMinimum getUser() {
        return user;
    }

}
