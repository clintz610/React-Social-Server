package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepo;

    // Get all profiles
    @GetMapping("/profile/findall")
    public List<Profile>getAllProfiles() {
        return profileRepo.findAll();
    }

    // Get profile by id
    @GetMapping("/profile/{id}")
    public Optional<Profile>findProfileById(@PathVariable int id) {
        return profileRepo.findById(id);
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
    
}
