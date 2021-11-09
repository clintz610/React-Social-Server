package com.revature.testcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.ReverbApplication;
import com.revature.controllers.ProfileController;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.ProfileRepository;

	@SpringBootTest(classes = ReverbApplication.class)
	@RunWith(SpringRunner.class)
	public class TestProfileControllerIntegration {
	
		@Autowired
		private ProfileController profilectrl;
	
		@Autowired
		private ProfileRepository profilereptest;
		
//		@Test
//		public void findProfileById_integration() throws Exception {
//	
//			ResponseEntity<Profile> retrievedProfile = profilectrl.findProfileById(1);
//	
//			assertThat("Lion").isEqualTo(retrievedProfile.getBody().getFirst_name());
//	
//		}

}
