package se.netwomen.NetWomenBackend.service;

import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.repository.DTO.PostRepository;
import se.netwomen.NetWomenBackend.resource.param.PostParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository repository) {
        this.postRepository = repository;
    }

    public List<Post> getPosts(PostParam param) {
        if (param.page != null) {
            return makePaging(postRepository.findAllOrderedByTimeStamp(), param);
        } else{
            List<Post> posts = Lists.newArrayList(postRepository.findAll());
            return posts;
        }
    }

    private List<Post> makePaging(Iterable iterablePosts, PostParam param) {
        List<Post> tempPostList = Lists.newArrayList(iterablePosts);
        List<Post> pagedPosts = new ArrayList<>();
        if ((param.page * param.numbersPerPage - param.numbersPerPage) < tempPostList.size()) {
            for (int i = (param.numbersPerPage * param.page) - param.numbersPerPage - 1; i <= param.numbersPerPage * param.page - 1; i++) {
                if (tempPostList.get(i) != null) {
                    pagedPosts.add(tempPostList.get(i));
                }
            }
        }
        return pagedPosts;
    }
}
