package com.revature.controllers;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
