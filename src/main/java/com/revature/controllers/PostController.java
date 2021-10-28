package com.revature.controllers;

import com.revature.models.Post;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<Post>> getPosts()
    {
        return ResponseEntity.ok(postService.getPosts());
    }

    @PostMapping(path = "/submit")
    public ResponseEntity<Post> submitPost(@RequestBody Post post)
    {
        try
        {
            return ResponseEntity.ok(postService.addNewPost(post));
        }
        catch(Exception e)
        {
            System.out.println("post failed");
            return ResponseEntity.ok(new Post());
        }
    }
}
