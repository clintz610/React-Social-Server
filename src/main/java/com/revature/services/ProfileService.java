package com.revature.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.WrongUserException;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;

@Service
public class ProfileService {

	// dependency injection
    @Autowired
    private ProfileRepository profileRepo;

	/*  Parameters: Profile object, User object
		Updates profile in database if User is owner
		Returns the modified Profile
	 */
    public Profile updateProfile(Profile profile, User user) throws WrongUserException {
    	if(profile.getUser().equals(user)) {
    		return profileRepo.saveAndFlush(profile);
    	} else {
    		throw new WrongUserException();
    	}	
    }

	/*  Parameter: profileID
		Returns the specified Profile
	 */
    public Profile findProfileById(int profileId) throws ProfileNotFoundException {
    	Optional<Profile> profile = profileRepo.findById(profileId);
    	
    	if(profile.isPresent()) {
    		return profile.get();
    	} else {
    		throw new ProfileNotFoundException();
    	}
    }

	/*  Parameter: User object
		Returns the Profile of the provided User
	 */
    public Profile findUsersProfile(User user) throws ProfileNotFoundException {
    	Optional<Profile> profile = profileRepo.getProfileByUser(user);
    	if(profile.isPresent()) {
    		return profile.get();
    	} else {
    		throw new ProfileNotFoundException();
    	}
    }

	public Boolean checkProfileOwnership(int id, User user) throws ProfileNotFoundException {
		Optional<Profile> profile = profileRepo.findById(id);
    	
    	if(profile.isPresent()) {
    		return profile.get().getUser().equals(user);
    	} else {
    		throw new ProfileNotFoundException();
    	}
	}
}
