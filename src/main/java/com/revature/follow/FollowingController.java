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
//    private User currentUser;

    @Autowired
    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    //current user only holds id and email
    @GetMapping(path = "/get-followings/{userId")
    public ResponseEntity<List<User>> getListOfFollowings(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(followingService.getFollowings(currentUser));
    }

    @GetMapping(path = "/get-followers/{userId")
    public ResponseEntity<List<User>> getListOfFollowers(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(followingService.getFollowers(currentUser));
    }

    @GetMapping(path = "/get-following-number/{userId}")
    public ResponseEntity<Integer> getNumberOfFollowing(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(followingService.getFollowingNumber(currentUser));
    }

    @GetMapping(path = "/get-follower-number/{userId}")
    public ResponseEntity<Integer> getNumberOfFollowers(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(followingService.getFollowerNumber(currentUser));
    }

    @PutMapping(path = "/follow-user/{followUserId}")
    public void followUser(@PathVariable String followUserId, @AuthenticationPrincipal User currentUser) {

        try {
            followingService.followUser(currentUser, followUserId);
            ResponseEntity.ok();
        }
        catch(Exception e) {
            e.printStackTrace();
            ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(path = "/unfollow-user/{unfollowUserId}")
    public void unfollowUser(@PathVariable String unfollowUserId, @AuthenticationPrincipal User currentUser) {

        try {
            followingService.unFollowUser(currentUser, unfollowUserId);
            ResponseEntity.ok();
        }
        catch(Exception e) {
            e.printStackTrace();
            ResponseEntity.internalServerError().build();
        }
    }
}

