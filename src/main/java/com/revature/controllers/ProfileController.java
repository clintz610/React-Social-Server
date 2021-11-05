package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/profile")
public class ProfileController {

    private final ProfileRepository profileRepo;

    public ProfileController(ProfileRepository profileRepo) {
        this.profileRepo = profileRepo;
    }

    /*
     * Get all of the profiles present in the database.
     * no parameters
     * returns List<Profile> */
    @GetMapping("/findall")
    public List<Profile>getAllProfiles() {
         return profileRepo.findAll();
    }

    /*
     * Get Profile from the database by ID.
     * Requires the integer id in the URL call
     * returns Optional<Profile> */
    @GetMapping("/{id}")
    public Optional<Profile>findProfileById(@PathVariable int id) {
         return profileRepo.findById(id);
    }

    /*
     * Get Profile of one specific user.
     * requires a User object
     * returns Optional<Profile> */
    @GetMapping("/getUsersProfile")
    public ResponseEntity<Optional<Profile>> findThisUsersProfile(@AuthenticationPrincipal User user) {
    	return ResponseEntity.ok(profileRepo.getProfileByUser(user));
    }
}
