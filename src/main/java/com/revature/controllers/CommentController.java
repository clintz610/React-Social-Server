package com.revature.controllers;

import com.revature.exceptions.UnauthorizedDeleteException;
import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Comment> submitComment(@RequestBody Comment comment, @PathVariable Long postId, @AuthenticationPrincipal User user)
    {
    	System.out.println("Trying to add a comment!");
        try
        {
            return ResponseEntity.ok(commentService.addNewComment(comment, postId, user));
        }
        catch(Exception e)
        {
            System.out.println("comment failed");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(path = "/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal User user)
    {
        try{
            commentService.deleteComment(commentId, user);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
}
