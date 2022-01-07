package com.revature.follow;

import com.revature.users.User;
import com.revature.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FollowingController {

    private final FollowingService followingService;
    private final UserRepository userRepository;
//    private User currentUser;

    @Autowired
    public FollowingController(FollowingService followingService, UserRepository userRepository) {
        this.followingService = followingService;
        this.userRepository = userRepository;
    }

    //get user id from profile id
    @GetMapping(path = "/profile/{profileId}")
    public String getUserFromProfileId(@PathVariable String profileId) {
        User user = followingService.getUserFromProfile(profileId);
        return user.getId();
    }

    //current user only holds id and email
    @GetMapping(path = "/get-followings/{userId}")
    public ResponseEntity<List<User>> getListOfFollowings(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(followingService.getFollowings(currentUser));
    }

    @GetMapping(path = "/get-owner-followers/{userId}")
    public ResponseEntity<List<User>> getListOfFollowers(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(followingService.getFollowers(currentUser));
    }

    @GetMapping(path = "/get-followers/{userId}")
    public Integer getFollowerNumber(@PathVariable String userId){
        User user = userRepository.getById(userId);
        return user.getFollower().size();
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

    @DeleteMapping(path = "/unfollow-user/{unfollowUserId}")
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

