package com.revature.testcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.ReverbApplication;
import com.revature.controllers.ProfileController;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestControllerIntegration {

	@Autowired
	private ProfileController profilectrl;
	
	@Autowired
	private UserService userservice;

	
	
	 @Test
	 public void profileCreationTest() throws Exception {
		 
		 User testuser = new User("587722673625gfs78dv6987a6svf9","76876v8g7v68@email.com");
		 
		 userservice.registerUser(testuser);
		 
		 ResponseEntity<Profile> retrievedProfile = profilectrl.findThisUsersProfile(testuser);
		
		 assertThat(testuser.getUid()).isEqualTo(retrievedProfile.getBody().getUser().getUid());
	 	
	 	
	}
	 
	 @Test
	 public void updateProfileTest() throws Exception {
		 
		 User testuser2 = new User("587722673625gfs78dv6987a6svf7","76876v8g6v68@email.com");
		 
		 userservice.registerUser(testuser2);
		 		 
		 ResponseEntity<Profile> updatedprofile = profilectrl.updateProfile(profilectrl.findThisUsersProfile(testuser2).getBody(), testuser2);

		 assertThat(updatedprofile.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}
	 
	 @Test
	 public void findProfileByIdTest() throws Exception {
		 
		 User testuser3 = new User("587722673625gfs78dv46987a6svf7","76876v84g6v68@email.com");
		 
		 userservice.registerUser(testuser3);
		 		 
		 ResponseEntity<Profile> foundprofile = profilectrl.findProfileById(1);

		 assertThat(foundprofile.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}
	 
	 @Test
	 public void checkProfileOwnershipTest() throws Exception {
		 
		 User testuser4 = new User("5877223625gfs78dv46987a6svf7","766v84g6v68@email.com");
		 
		 userservice.registerUser(testuser4);
		 		 
		 ResponseEntity<Profile> assignedprofile = profilectrl.findThisUsersProfile(testuser4);

		 assertThat((profilectrl.checkProfileOwnership(assignedprofile.getBody().getId(),testuser4)).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}
}
