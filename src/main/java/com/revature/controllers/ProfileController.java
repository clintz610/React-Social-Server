package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/profile")
public class ProfileController {

    private final ProfileRepository profileRepo;

    public ProfileController(ProfileRepository profileRepo) {
        this.profileRepo = profileRepo;
    }

    @GetMapping("/findall")
    public List<Profile>getAllProfiles() {
        return profileRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Profile>findProfileById(@PathVariable int id) {
        return profileRepo.findById(id);
    }  
    
    @GetMapping("/getUsersProfile")
    public Optional<Profile> findThisUsersProfile(@AuthenticationPrincipal User user) {
    	return profileRepo.getProfileByUser(user);
    }
    
}
