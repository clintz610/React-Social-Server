package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private ProfileRepository profileRepository;

    // constructor
    @Autowired
    UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    /*  Parameter: User object
        Creates user in the database with a blank Profile
        Returns nothing (void)
     */
    public void registerUser(User user) {
        userRepository.save(user);
        Profile profile = new Profile();
        profile.setUser(user);
		profileRepository.save(profile);
    }

    /*  Parameter: User
        Finds a User object in the database
        Returns nothing (void)
     */
    public void loginUser(User user) {
        Optional<User> dbUser = userRepository.findById(user.getUid());
        if (!dbUser.isPresent()) {
            userRepository.save(user);
        }
    }
}
