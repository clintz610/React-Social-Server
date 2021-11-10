package com.revature.testcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	 public void devops_profile_test() throws Exception {

		 User testuser = new User("587722673625gfs78dv6987a6svf9","76876v8g7v68@email.com");
		 
		 userservice.registerUser(testuser);
		 
	 	ResponseEntity<Profile> retrievedProfile = profilectrl.findThisUsersProfile(testuser);

	 	assertThat(testuser.getUid()).isEqualTo(retrievedProfile.getBody().getUser().getUid());

	}

}
