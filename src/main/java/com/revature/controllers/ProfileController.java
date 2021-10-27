package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepo;

    @GetMapping("/profile/findall")
    public List<Profile>getAllProfiles() {
        return profileRepo.findAll();
    }

    @GetMapping("/profile/{id}")
    public Optional<Profile>findProfileById(@PathVariable int id) {
        return profileRepo.findById(id);
    }  
    
}
