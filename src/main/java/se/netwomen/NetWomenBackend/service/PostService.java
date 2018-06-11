package se.netwomen.NetWomenBackend.service;

import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete;
import se.netwomen.NetWomenBackend.model.data.PostLike;
import se.netwomen.NetWomenBackend.repository.DTO.PostLikeRepository;
import se.netwomen.NetWomenBackend.repository.DTO.PostRepository;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.resource.param.PostParam;
import se.netwomen.NetWomenBackend.service.Parsers.PostParser;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public List<PostComplete> getPostsAndLikes(PostParam param) {
        if (param.page != null) {
            return makePaging(postRepository.findAllOrderedByTimeStamp(), param);
        } else {
            List<PostComplete> posts = PostParser.postToPostCompleteList(postRepository.findAll());
            return posts;
        }
    }

    private List<PostComplete> makePaging(Iterable iterablePosts, PostParam param) {
        List<Post> tempPostList = Lists.newArrayList(iterablePosts);
        List<PostComplete> pagedPosts = new ArrayList<>();
        int start = param.page * param.numbersPerPage - param.numbersPerPage;
        int finish = param.numbersPerPage * param.page - 1;
        if (start < tempPostList.size()) {
            for (int i = start; i <= finish; i++) {
                if (i <= (tempPostList.size() - 1)) {
                    int likesCount = postLikeRepository.countPostLikesByPostId(tempPostList.get(i).getId());
                    pagedPosts.add(new PostComplete(tempPostList.get(i), likesCount));
                }
            }
        }
        return pagedPosts;
    }

    public boolean validateCookie(HttpHeaders header) {
        if (header.getCookies().get("name") != null) {
            Cookie cookie = header.getCookies().get("name");
            if (userRepository.findUsersCookie(cookie.getValue()) == 1)
                return true;
        }
        return false;
    }

    public List<HashMap<Long, List<PostLike>>> getPostLikes(List<Post> posts) {
        List<HashMap<Long, List<PostLike>>> postLikesList = new ArrayList<>();
        HashMap<Long, List<PostLike>> postAndLikesMap = new HashMap<>();
        for (Post post : posts) {
            List<PostLike> tempPostLikes = new ArrayList<>();
            tempPostLikes = postLikeRepository.findAllByPostId(post.getId());
            postAndLikesMap.put(post.getId(), tempPostLikes);
        }
        postLikesList.add(postAndLikesMap);
        return postLikesList;
    }
}
