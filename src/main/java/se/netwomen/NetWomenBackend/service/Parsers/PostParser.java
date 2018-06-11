package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete;

import java.util.ArrayList;
import java.util.List;

public final class PostParser {

    public static List<PostComplete> postToPostCompleteList(List<Post> postList){
        List<PostComplete> postCompletes = new ArrayList<>();
        postList.forEach(p -> postCompletes.add(new PostComplete(p, 0)));
        return postCompletes;
    }

    public static List<PostComplete> postToPostCompleteList(Iterable<Post> postList){
        List<PostComplete> postCompletes = new ArrayList<>();
        postList.forEach(p -> postCompletes.add(new PostComplete(p, 0)));
        return postCompletes;
    }

}
