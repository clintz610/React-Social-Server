package com.revature.controllers;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/like")
public class LikeController {
    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService)
    {
        this.likeService = likeService;
    }

    @GetMapping(path = "/get-number-of-likes/{postId}")
    public ResponseEntity<Integer> getNumberOfLikes(@PathVariable Long postId)
    {
        try {
            return ResponseEntity.ok(likeService.getNumberofLikes(postId));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }

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
           // ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(path = "/check-if-liked")
    public void checkIfLiked(@PathVariable Long postId, @AuthenticationPrincipal User user)
    {

    }
}
