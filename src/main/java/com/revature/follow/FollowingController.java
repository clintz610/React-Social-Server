package com.revature.follow;

import com.revature.users.User;
import com.revature.follow.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FollowingController {

    private final FollowingService followingService;

    @Autowired
    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @GetMapping(path = "/get-following/{userId}")
    public ResponseEntity<List<User>> getNumberOfFollowing() {
        return ResponseEntity.ok(followingService.getFollowings());}
    @GetMapping(path = "get-followers/{userId}")
    public ResponseEntity<List<User>> getNumberOfFollowers() {return ResponseEntity.ok(followingService.getFollowers());}

    @PutMapping(path = "/follow-user/{followUserId}")
    public void followUser(@PathVariable User followUser, @AuthenticationPrincipal User currentUser)
    {
        String followUserId = followUser.getId();

        try {
            followingService.followUser(currentUser, followUser);
            ResponseEntity.ok();
        }
        catch(Exception e)
        {
//            e.printStackTrace();
            ResponseEntity.internalServerError().build();
        }

    }

}

