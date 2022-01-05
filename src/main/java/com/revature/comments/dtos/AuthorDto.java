package com.revature.comments.dtos;

import com.revature.exceptions.ProfileNotFoundException;
import com.revature.users.User;
import com.revature.users.profiles.Profile;
import com.revature.users.profiles.ProfileRepository;
import com.revature.users.profiles.ProfileService;
import lombok.Data;

import java.util.Optional;

@Data
public class AuthorDto {
    private String id;
    private String firstname;
    private String lastname;
    private String pfId;

    public AuthorDto (User raw, ProfileRepository profileRepository) {
        // Set the user id from the given user
        this.id = raw.getId();


        Optional<Profile> optionalProfile = profileRepository.getProfileByUser(raw);
        if (optionalProfile.isPresent()){
            Profile rawProf = optionalProfile.get();
            this.firstname = rawProf.getFirst_name();
            this.lastname = rawProf.getLast_name();
            this.pfId = rawProf.getId();
        }
        else {
            this.firstname = null;
            this.lastname = null;
            this.pfId = null;
        }




    }
}
