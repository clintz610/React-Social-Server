package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    private final ProfileRepository profileRepo;

    public ProfileController(ProfileRepository profileRepo) {
        this.profileRepo = profileRepo;
    }

    // Get all profiles
    @GetMapping("/profile/findall")
    public List<Profile>getAllProfiles() {
        return profileRepo.findAll();
    }

    // Get profile by id
    @GetMapping("/profile/findprofile")
    public ResponseEntity<Optional<Profile>> findProfileById(@AuthenticationPrincipal User user) {
        //user_id = user.getUid();
        //Optional<Profile> profile = profileRepo.findByUserId(user_id);
        Optional<Profile> profile = profileRepo.findById(1);
        return ResponseEntity.ok(profile);
    }  

    // Update profile
    // Directly map a request json object into a java object for using requestbody annotation
    @PutMapping("/profile/{id}")
    public Optional<Profile> updateProfile(@PathVariable int id, @RequestBody Profile profileDetail) {
        Profile profile = new Profile();
        profile.setFirst_name(profileDetail.getFirst_name());
        profile.setLast_name(profileDetail.getLast_name());
        profile.setProfile_img(profileDetail.getProfile_img());
        profile.setHeader_img(profileDetail.getHeader_img());
        profile.setAbout_me(profileDetail.getAbout_me());
        return null;               

    }

    /*
    1. user registers
    2. a request is made to the backend to create that user model
    3. in the previous request, a profile is also created and associated
       to the new user
    4. they go to their profile page
    5. a get request is made to get their profile, which is default
    6. they make an edit to their profile
    7. you make a put request to the backed including the updated profile
    8. you take the request body and saveandflush it
    9. the user is redirected back to their profile, and a new get request is made
    10. the new profile is returned and shown on the page
    */
    
}
