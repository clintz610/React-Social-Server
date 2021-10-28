package com.revature.controllers;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService)
    {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getPosts()
    {
        return ResponseEntity.ok(commentService.getComments());
    }

    @PostMapping(path = "/submit")
    public ResponseEntity<Comment> submitPost(@RequestBody Comment comment)
    {
        try
        {
            return ResponseEntity.ok(commentService.addNewComment(comment));
        }
        catch(Exception e)
        {
            System.out.println("post failed");
            return ResponseEntity.ok(new Comment());
        }
    }


}
