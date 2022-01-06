package com.revature.users.profiles;

import java.util.Optional;
import java.util.UUID;

import com.revature.users.dtos.ProfileResponse;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WrongUserException;
import com.revature.users.User;

@Service
public class ProfileService {

	// dependency injection

    private ProfileRepository profileRepo;

	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepo = profileRepository;
	}


	/*  Parameters: Profile object, User object
		Updates profile in database if User is owner
		Returns the modified Profile
	 */
    public Profile updateProfile(Profile profile, User user) throws WrongUserException {
    	if(profile.getUser().equals(user)) {
			Profile outputTest = profileRepo.saveAndFlush(profile);
			System.out.println(outputTest);
    		return outputTest;
    	} else {
    		throw new WrongUserException();
    	}	
    }

	/*  Parameter: profileID
		Returns the specified Profile
	 */
    public Profile findProfileById(UUID profileId) throws UserNotFoundException {
    	Optional<Profile> profile = profileRepo.findById(profileId);
    	
    	if(profile.isPresent()) {
    		return profile.get();
    	} else {
    		throw new UserNotFoundException();
    	}
    }


	/*  Parameter: User object
		Returns the Profile of the provided User
	 */
    public Profile findUsersProfile(User user) throws UserNotFoundException {
    	Optional<Profile> profile = profileRepo.getProfileByUser(user);
    	if(profile.isPresent()) {
    		return profile.get();
    	} else {
    		throw new UserNotFoundException();
    	}
    }


	public Boolean checkProfileOwnership(UUID id, User user) throws UserNotFoundException {
		Optional<Profile> profile = profileRepo.findById(id);
    	
    	if(profile.isPresent()) {
    		return profile.get().getUser().equals(user);
    	} else {
    		throw new UserNotFoundException();
    	}
	}
}
