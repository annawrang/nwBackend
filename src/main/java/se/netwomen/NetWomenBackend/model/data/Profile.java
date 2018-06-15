package se.netwomen.NetWomenBackend.model.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Profile {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    @OneToOne
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
