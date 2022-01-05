package com.revature.follow;

import com.revature.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FollowingController {

    private final FollowingService followingService;
    private final User currentUser;

    @Autowired
    public FollowingController(FollowingService followingService, User currentUser) {
        this.followingService = followingService;
        this.currentUser = currentUser;
    }

    @GetMapping(path = "/get-followings/{userId")
    public ResponseEntity<List<User>> getListOfFollowings() {
        return ResponseEntity.ok(followingService.getFollowings(currentUser));
    }

    @GetMapping(path = "/get-followers/{userId")
    public ResponseEntity<List<User>> getListOfFollowers() {
        return ResponseEntity.ok(followingService.getFollowers(currentUser));
    }

    @GetMapping(path = "/get-following-number/{userId}")
    public ResponseEntity<Integer> getNumberOfFollowing() {
        return ResponseEntity.ok(followingService.getFollowingNumber(currentUser));
    }

    @GetMapping(path = "/get-follower-number/{userId}")
    public ResponseEntity<Integer> getNumberOfFollowers() {
        return ResponseEntity.ok(followingService.getFollowerNumber(currentUser));
    }

    @PutMapping(path = "/follow-user/{followUserId}")
    public void followUser(@PathVariable User followUser, @AuthenticationPrincipal User currentUser) {
        String followUserId = followUser.getId();

        try {
            followingService.followUser(currentUser, followUser);
            ResponseEntity.ok();
        }
        catch(Exception e) {
            e.printStackTrace();
            ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(path = "/unfollow-user/{userId}")
    public void unfollowUser(@PathVariable User unfollowUser, @AuthenticationPrincipal User currentUser) {
        String unfollowUserId = unfollowUser.getId();

        try {
            followingService.unfollowUser(currentUser, unfollowUser);
            ResponseEntity.ok();
        }
        catch(Exception e) {
            e.printStackTrace();
            ResponseEntity.internalServerError().build();
        }
    }
}

