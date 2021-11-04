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

    /*
     * Get all of the comments present in the database.
     * no parameters
     * returns List<Comment> */
    @GetMapping(path = "/get-all-comments")
    public ResponseEntity<List<Comment>> getComments()
    {
        return ResponseEntity.ok(commentService.getComments());
    }

    /*
     * Submit a comment to the database.
     * parameters: JSON Comment, Long postId through path variable
     * returns Comment */
    @PostMapping(path = "/submit/{postId}")
    public ResponseEntity<Comment> submitComment(@RequestBody Comment comment, @PathVariable Long postId)
    {
        try
        {
            return ResponseEntity.ok(commentService.addNewComment(comment, postId));
        }
        catch(IllegalStateException illegalStateException)
        {
            System.out.println(illegalStateException.getMessage());
            return ResponseEntity.ok(new Comment());
        }
        catch(Exception e)
        {
            System.out.println("comment failed");
            return ResponseEntity.ok(new Comment());
        }
    }

    @PostMapping(path = "/delete")
    public void deleteComment(@RequestBody Comment comment)
    {
        try{
            commentService.deleteComment(comment);
        }
        catch(IllegalStateException illegalStateException)
        {
            System.out.println(illegalStateException.getMessage());
        }
    }

}
