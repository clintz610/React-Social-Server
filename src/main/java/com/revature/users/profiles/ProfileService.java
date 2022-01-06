package com.revature.users.profiles;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.WrongUserException;
import com.revature.users.profiles.Profile;
import com.revature.users.User;
import com.revature.users.profiles.ProfileRepository;

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
    	if(profile.getUser().getId().equals(user.getId())) {
//			System.out.println("pass user verification");
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
