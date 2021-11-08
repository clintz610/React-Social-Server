package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/like")
public class LikeController {
    private LikeService likeService;

    //constructor
    @Autowired
    public LikeController(LikeService likeService)
    {
        this.likeService = likeService;
    }

    /*  Must be given postID through URL
        Returns the number of likes for identified post
     */
    @GetMapping(path = "/get-number-of-likes/{postId}")
    public ResponseEntity<Integer> getNumberOfLikes(@PathVariable Long postId)
    {
        try {
        	Integer numLikes = likeService.getNumberofLikes(postId);
        	return ResponseEntity.ok(numLikes);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }

    /*  Must be provided postID in URL
        Creates a new like upon identified post.
     */
    @PutMapping(path = "/like-post/{postId}")
    public void likePost(@PathVariable Long postId, @AuthenticationPrincipal User user)
    {
        try {
            likeService.likePost(postId, user);
            ResponseEntity.ok();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ResponseEntity.internalServerError().build();
        }

    }

    /*  Must be provide a postID in the URL
        Returns boolean if logged in user has already liked the post.
     */
    @GetMapping(path = "/check-if-liked/{postId}")
    public ResponseEntity<Boolean> checkIfLiked(@PathVariable Long postId, @AuthenticationPrincipal User user)
    {
    	try {
    		return ResponseEntity.ok(likeService.checkIfAlreadyLiked(postId, user));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return ResponseEntity.internalServerError().build();
    }
}
