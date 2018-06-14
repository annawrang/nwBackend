package se.netwomen.NetWomenBackend.model.data;

import java.util.UUID;

/*  This class is what is sent back when we need a user that's connected
* to a post, comment, like etc. Where we don't want all the information */

public final class UserMinimum {

    private String firstName;
    private String surName;
    private String email;

    public UserMinimum(String firstName, String surName, String email) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
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
}
