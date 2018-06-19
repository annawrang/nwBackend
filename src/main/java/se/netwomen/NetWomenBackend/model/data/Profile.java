package se.netwomen.NetWomenBackend.model.data;

public final class Profile {

    private Long id;
    private String description;
    private User user;

    protected Profile(){}

    public Profile(String description, User user) {
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }
}
