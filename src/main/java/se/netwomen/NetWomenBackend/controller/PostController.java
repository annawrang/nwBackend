package se.netwomen.NetWomenBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import se.netwomen.NetWomenBackend.controller.security.JwtTokenProvider;
import se.netwomen.NetWomenBackend.controller.validator.PostValidator;
import se.netwomen.NetWomenBackend.model.data.Post;
import se.netwomen.NetWomenBackend.model.data.PostComplete.PostComplete;
import se.netwomen.NetWomenBackend.service.PostService;
import se.netwomen.NetWomenBackend.service.exceptions.EmailNotFoundException;

import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Context
    private UriInfo uriInfo;
    @Context
    private HttpHeaders requestHeaders;
    private final PostService postService;
    private PostValidator postValidator;

    @Autowired
    public PostController(PostService service, JwtTokenProvider jwtTokenProvider) {
        this.postService = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(postValidator);
    }

    @DeleteMapping(path = "/{postNumber}")
    public ResponseEntity deletePost(@PathVariable String postNumber) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        postService.deletePost(postNumber, userNumber);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity createNewPost(@Valid @RequestBody Post post) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        postService.saveNewPost(userNumber, post);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(path = "/{postNumber}")
    public ResponseEntity editPost(@Valid @PathVariable String postNumber, @RequestBody String newText) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        postService.editPost(postNumber, userNumber, newText);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(path = "/{postNumber}/comments")
    public ResponseEntity createNewPostComment(@PathVariable String postNumber, @RequestBody String newComment) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        postService.createPostComment(postNumber, userNumber, newComment);
        return new ResponseEntity(HttpStatus.CREATED);

    }

    @PostMapping(path = "/{postNumber}/comments/{commentNumber}/reply")
    public ResponseEntity createNewPostCommentReply(@PathVariable String postNumber,
                                                    @PathVariable String commentNumber,
                                                    @RequestBody String newComment) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        postService.createPostCommentReply(commentNumber, userNumber, newComment);
        return new ResponseEntity(HttpStatus.CREATED);

    }

    @PostMapping(path = "/{postNumber}/comments/{commentNumber}/like")
    public ResponseEntity saveNewCommentLike(@PathVariable String postNumber,
                                             @PathVariable String commentNumber) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        postService.createPostCommentLike(userNumber, postNumber, commentNumber);
        return new ResponseEntity(HttpStatus.CREATED);

    }


    /*
     *
     * Till profile - get Users posts
     * */
    @GetMapping(path = "/user/{userNumber}")
    public ResponseEntity getPostsByUser(@PathVariable String userNumber,
                                         @RequestParam(defaultValue = "1", required = false) int page,
                                         @RequestParam(defaultValue = "10", required = false) int numbersPerPage) {
        List<PostComplete> posts = postService.getUsersPosts(userNumber, page, numbersPerPage);
        return new ResponseEntity(posts, HttpStatus.OK);
    }


    // Paging, vilken sida + antal per sida (default 10 per sida)
    @GetMapping
    public ResponseEntity getPostsPaged(@RequestParam(defaultValue = "1", required = false) int page,
                                        @RequestParam(defaultValue = "10", required = false) int numbersPerPage) {
        List<PostComplete> posts = postService.getPostsAndLikesComments(page, numbersPerPage);
        return new ResponseEntity(posts, HttpStatus.OK);
    }

    @GetMapping(path = "/{postNumber}")
    public ResponseEntity getPost(@PathVariable String postNumber) {
        return new ResponseEntity(postService.getPost(postNumber), HttpStatus.OK);
    }


    @PostMapping(value = "/{postNumber}/likes")
    public ResponseEntity createNewPostLike(@PathVariable String postNumber) {
        String userNumber = getUserNumberFromAuth(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
        if (userNumber != null) {
            postService.saveNewPostLike(postNumber, userNumber);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    // This is the method use to get the usernumber from the token, to always know which user is
    // sending the request
    private String getUserNumberFromAuth(String auth) {
        if (auth == null) {
            throw new EmailNotFoundException("Usernumber not found.");
        }
        String userNumber = auth.split(";")[0];
        userNumber = userNumber.substring(userNumber.lastIndexOf(":") + 2).trim();
        System.out.println("userNumber==" + userNumber);
        return userNumber;
    }

}
