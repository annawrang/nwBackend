package se.netwomen.NetWomenBackend.model.data;

public final class Profile {

    private Long id;
    private String description;
    private UserMinimum userMinimum;

    protected Profile(){}

    public Profile(UserMinimum userMinimum, String description) {
        this.userMinimum = userMinimum;
        this.description = description;
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
