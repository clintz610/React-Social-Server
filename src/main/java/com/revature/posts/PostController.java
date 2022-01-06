package com.revature.posts;

import com.revature.posts.dtos.NewPostRequest;
import com.revature.posts.dtos.PostResponse;
import com.revature.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    /*
    * Get all of the post present in the database.
    * no parameters
    * returns List<Post> */
    @GetMapping(path = "/get-all-posts")
    public ResponseEntity<List<PostResponse>> getPosts()
    {
        return ResponseEntity.ok(postService.getPosts());
    }


    /*
     * Submit a post to the database.
     * Post JSON as a parameter
     * returns a Post */


    @PostMapping(path = "/submit")
    public ResponseEntity<Post> submitPost(@RequestBody NewPostRequest post, @AuthenticationPrincipal User user)
    {
        try
        {
        	Post postToReturn = postService.addNewPost(post, user);
            return ResponseEntity.ok(postToReturn);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.ok(new Post());
        }
    }



}
