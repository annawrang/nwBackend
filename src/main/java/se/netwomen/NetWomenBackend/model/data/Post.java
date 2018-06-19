package se.netwomen.NetWomenBackend.model.data;

import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;

import java.sql.Timestamp;

public final class Post {

    private UserMinimum user;
    private String text;
    private String pictureUrl;
    private Timestamp creationTimestamp;
    private String postNumber;

    protected Post(){}

    public Post(UserMinimum user, String text, String pictureUrl) {
        this.user = user;
        this.text = text;
        this.pictureUrl = pictureUrl;
    }

    public Post(UserMinimum user, String text, String pictureUrl, Timestamp creationTimestamp, String postNumber) {
        this.user = user;
        this.text = text;
        this.pictureUrl = pictureUrl;
        this.creationTimestamp = creationTimestamp;
        this.postNumber = postNumber;
    }

    public UserMinimum getUser() {
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

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }
}
