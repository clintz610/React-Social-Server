package com.revature.users.profiles;

import com.revature.users.UserService;
import com.revature.users.dtos.ProfileRequest;
import com.revature.users.dtos.ProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WrongUserException;
import com.revature.users.User;

import java.util.UUID;


@RestController
@RequestMapping(path = "/api/profile")
public class ProfileController {

	private final ProfileService profileService;


	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	/*
	 * Get Profile from the database by ID. Requires the integer id in the URL call
	 * returns Optional<Profile>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ProfileResponse> findProfileById(@PathVariable String id) {
		try {

			return ResponseEntity.ok(new ProfileResponse(profileService.findProfileById(UUID.fromString(id))));
		} catch (UserNotFoundException e) {
//			e.printStackTrace();
			return ResponseEntity.status(404).build();
		}
	}

	/*
	 * Get the profile by the author's ID.
	 * returns the profile
	 */
	@GetMapping("/getByAuthor/{id}")
	public ResponseEntity<ProfileResponse> findProfileByAuthor(@PathVariable String id) {
		try {
			User query = new User();
			query.setId(id);
			return ResponseEntity.ok(new ProfileResponse(profileService.findUsersProfile(query)));
		} catch (UserNotFoundException e) {
			//e.printStackTrace();
			return ResponseEntity.status(404).build();
		}
	}

	/*  Must be give a Profile object in the Request body.
		Updates the Profile in the database.
		Returns the updated Profile.
	 */
	@PutMapping("/update")
	public ResponseEntity<ProfileResponse> updateProfile(@RequestBody ProfileRequest profile, @AuthenticationPrincipal User user) {
		try {
			// Reconstruct the profile from the DTO
			Profile updateTarget = new Profile();
			updateTarget.setId(UUID.fromString(profile.getId()));
			updateTarget.setAboutMe(profile.getAbout_me());
			updateTarget.setBirthday(profile.getBirthday());
			updateTarget.setFirstName(profile.getFirst_name());
			updateTarget.setLastName(profile.getLast_name());
			updateTarget.setHobby(profile.getHobby());
			updateTarget.setLocation(profile.getLocation());
			updateTarget.setProfileImg(profile.getProfile_img());
			updateTarget.setHeaderImg(profile.getHeader_img());
			updateTarget.setUser(user);

			return ResponseEntity.ok(new ProfileResponse(profileService.updateProfile(updateTarget, user)));
		} catch (WrongUserException e) {
//			e.printStackTrace();
			return ResponseEntity.status(403).build();
		}
	}

	/*
	 * Get Profile of one specific user. requires a User object returns
	 * Optional<Profile>
	 */
	@GetMapping("/getUsersProfile")
	public ResponseEntity<ProfileResponse> findThisUsersProfile(@AuthenticationPrincipal User user) {
		try {
			return ResponseEntity.ok(new ProfileResponse(profileService.findUsersProfile(user)));
		} catch (UserNotFoundException e) {
//			e.printStackTrace();
			return ResponseEntity.status(404).build();
		}
	}

	/*  Must be provided an id in the URL
		Verifies whether or not the logged in user is the owner of that Profile
		Returns boolean value
	 */
	@GetMapping("/checkProfileOwnership/{id}")
	public ResponseEntity<Boolean> checkProfileOwnership(@PathVariable String id, @AuthenticationPrincipal User user) {
		try {
			return ResponseEntity.ok(profileService.checkProfileOwnership(UUID.fromString(id), user));
		} catch (UserNotFoundException e) {
//			e.printStackTrace();
			return ResponseEntity.status(404).build();
		}
	}


}