package com.revature.follow;

import com.revature.users.User;
import org.springframework.stereotype.Service;
import com.revature.users.UserRepository;

import java.util.List;

@Service
public class FollowingService {

    //TODO: create isValidUser helper method to clean up code

    private final UserRepository userRepository;
    private FollowRepository followRepository;

    //constructor
    public FollowingService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public List<User> getFollowings() {return followRepository.findAll();}

    public List<User> getFollowers() {return followRepository.findAll();}

    // Method to allow a user to follow another user
    public boolean followUser(User currentUser,User followUser) {
        try {
            List<User> followingList = followUser.getFollowing();
            for(int i = 0; i < followingList.size(); i++) {
                if (followingList.get(i).equals(currentUser)) {
                    return false; //TODO: change so that it throws custom error instead
                }
            }
            if (followUser == null) {
                return false; //TODO: change to exception (Enter invalid followUser)
            }
            followingList.add(currentUser);
            userRepository.save(followUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); //TODO: "probably connection error"
        }
        return false;
    }

    // Method to allow a user to unfollow another user
    public boolean unfollowUser(User currentUser, User unfollowUser) {
        try {
            List<User> followingList = unfollowUser.getFollowing();
            for(int i = 0; i < followingList.size(); i++) {
                if (!followingList.get(i).equals(currentUser)) {
                    return false; //TODO: change so taht it throws custom error instead
                }
            }
            if (unfollowUser == null) {
                return false; //TODO: change to exception (Enter invalid unfollowUser)
            }
            followingList.remove(currentUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); //TODO: "probably connection error"
        }
        return false;
    }
}
