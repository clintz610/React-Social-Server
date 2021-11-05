package com.revature.controllers;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<List<Post>> getPosts()
    {
        return ResponseEntity.ok(postService.getPosts());
    }


    /*
     * Submit a post to the database.
     * Post JSON as a parameter
     * returns a Post */
    @PostMapping(path = "/submit")
    public ResponseEntity<Post> submitPost(@RequestBody Post post, @AuthenticationPrincipal User user)
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

    @PostMapping(path = "/delete")
    public void deletePost(@RequestBody Post post)
    {
        try
        {
            postService.deletePost(post);
        }
        catch(IllegalStateException e)
        {
            e.printStackTrace();
        }
    }


    //get all post from a specific user.
    /*@GetMapping(path = "/user/posts")
    public ResponseEntity<List<Post>> getUserPosts()
    {
        return ResponseEntity.ok(postService.());
    }*/
}
