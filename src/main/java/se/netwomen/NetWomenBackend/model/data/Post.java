package se.netwomen.NetWomenBackend.model.data;

import java.sql.Timestamp;

public final class Post {

    private User user;
    private String text;
    private String pictureUrl;
    private Timestamp creationTimestamp;

    protected Post(){}

    public Post(User user, String text, String pictureUrl) {
        this.user = user;
        this.text = text;
        this.pictureUrl = pictureUrl;
    }

    public Post(User user, String text, String pictureUrl, Timestamp creationTimestamp) {
        this.user = user;
        this.text = text;
        this.pictureUrl = pictureUrl;
        this.creationTimestamp = creationTimestamp;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
