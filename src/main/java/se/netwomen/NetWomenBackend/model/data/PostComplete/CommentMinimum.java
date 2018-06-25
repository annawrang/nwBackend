package se.netwomen.NetWomenBackend.model.data.PostComplete;

public class CommentMinimum {
    private UserMinimum user;
    private String text;

    public CommentMinimum(UserMinimum user, String text) {
        this.user = user;
        this.text = text;
    }

    public UserMinimum getUser() {
        return user;
    }

    public String getText() {
        return text;
    }
}
