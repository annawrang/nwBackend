package se.netwomen.NetWomenBackend.model.data;

import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;

import java.time.LocalDateTime;

public final class Post {

    private UserMinimum user;
    private String text;
    private String pictureUrl;
    private LocalDateTime date;
    private String postNumber;

    protected Post() {
    }

    public Post(UserMinimum user, String text, String pictureUrl) {
        this.user = user;
        this.text = text;
        this.pictureUrl = pictureUrl;
    }

    public Post(UserMinimum user, String text, String pictureUrl, LocalDateTime date, String postNumber) {
        this.user = user;
        this.text = text;
        this.pictureUrl = pictureUrl;
        this.date = date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }
}
