package com.revature.services;

import java.util.List;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepo;

    public List<Profile> listProfiles() {
        return profileRepo.findAll();
    }
    
}
