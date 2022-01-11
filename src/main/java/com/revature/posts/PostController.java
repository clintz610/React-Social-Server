package com.revature.posts;

import com.revature.posts.dtos.NewPostRequest;
import com.revature.posts.dtos.PostResponse;
import com.revature.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /*
    * Get all of the post present in the database.
    * no parameters
    * returns List<Post> */
    @GetMapping(path = "/get-all-posts")
    public ResponseEntity<List<PostResponse>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    /**
     * @param groupName of group being queried
     * @return list of PostResponses attached to a given group
     */
    @GetMapping(path = "/get-group-posts/{groupName}")
    public ResponseEntity<List<PostResponse>> getGroupPosts(@PathVariable String groupName) {
        return ResponseEntity.ok(postService.getGroupPosts(groupName));

    }

    /**
     * @param user of logged-in user
     * @return list of PostResponses attached to a given user
     */
    @GetMapping(path = "/get-following-posts")
    public ResponseEntity<List<PostResponse>> getFollowingPosts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.getPostsOfFollowing(user.getId()));
    }

    @GetMapping(path = "/get-personal-posts")
    public ResponseEntity<List<PostResponse>> getPersonalPosts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.getPersonalPosts(user.getId()));
    }


    /**
     * Submit a post to the database.
     * Post JSON as a parameter
     * returns a Post */
    @PostMapping(path = "/submit")
    public ResponseEntity<Void> submitPost(@RequestBody NewPostRequest post, @AuthenticationPrincipal User user) {
        	postService.addNewPost(post, user);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

    }

}
