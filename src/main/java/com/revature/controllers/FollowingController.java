package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.FollowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FollowingController {

    private final FollowingService followingService;

    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @GetMapping(path = "/get-following")
    public ResponseEntity<List<User>> getFollowing() {return ResponseEntity.ok(followingService.getFollowings());}
}
