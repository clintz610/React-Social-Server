package com.revature.security.props.users;

import com.revature.security.props.users.profiles.Profile;
import com.revature.security.props.users.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
        Optional<User> dbUser = userRepository.findById(user.getId());
        if (!dbUser.isPresent()) {
            userRepository.save(user);
        }
    }
}
