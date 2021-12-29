package com.revature.testcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;
import com.revature.controllers.ProfileController;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.services.UserService;

@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestProfileControllerIntegration {
    /*
	
	 @Autowired
	 private ProfileController profilectrl;
	
	 @Autowired
	 private UserService userservice;
	
	
	
	 @Test
	 public void profileCreationTest() throws Exception {
		 
		 User testuser = new User("1877224573625gfs78dv69866svf9","16277g7v68@email.com");
		 
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
	 
	@Test
	public void updateProfileTestFailure() throws Exception {
		User testuser5 = new User("5877223625gfs78dv46987a6sv99","996v84g6v68@email.com");
		User testuser6 = new User("5877223625gfs78dv46987a6sv22","336v84g6v68@email.com");
		
		assertThat(profilectrl.findThisUsersProfile(testuser5)).isEqualTo(ResponseEntity.status(404).build());
		
		userservice.registerUser(testuser5);
		
		ResponseEntity<Profile> updatedprofile = profilectrl.updateProfile(profilectrl.findThisUsersProfile(testuser5).getBody(), testuser6);
			
		assertThat(updatedprofile).isEqualTo(ResponseEntity.status(403).build());
	}
	
	@Test
	public void  findThisUsersProfileFailure() throws Exception {
		User testuser7 = new User("5877223625gfs78dv46987a6sv69","69v84g6v68@email.com");
	
		ResponseEntity<Profile> newprofile = profilectrl.findThisUsersProfile(testuser7);
			
		assertThat(newprofile).isEqualTo(ResponseEntity.status(404).build());
	}
	@Test
	public void  findProfileByIdFailure() throws Exception {
		ResponseEntity<Profile> newprofile = profilectrl.findProfileById(-120941);
			
		assertThat(newprofile).isEqualTo(ResponseEntity.status(404).build());
	}
	@Test
	public void  checkProfileOwnershipFailure() throws Exception {
		User testuser8 = new User("5899223625gfs78dv46987a6sv69","42v84g6v68@email.com");
		ResponseEntity<Boolean> newprofile = profilectrl.checkProfileOwnership(-120941,testuser8);
			
		assertThat(newprofile).isEqualTo(ResponseEntity.status(404).build());
	}

     */
}
