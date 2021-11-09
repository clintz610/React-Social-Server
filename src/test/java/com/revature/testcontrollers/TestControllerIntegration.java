package com.revature.testcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.ReverbApplication;
import com.revature.controllers.ProfileController;
import com.revature.models.Profile;
import com.revature.repositories.ProfileRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-integration-testing.properties")
//@ActiveProfiles("test")
public class TestControllerIntegration {

	@Autowired
	private ProfileController profilectrl;

	@Autowired
	private ProfileRepository profilereptest;

	// @Test
	// public void devops_profile_test() throws Exception {

		Profile tester = new Profile();
		
	// 	profilereptest.save(tester);

	// 	ResponseEntity<Profile> retrievedProfile = profilectrl.findProfileById(tester.getId());

	// 	assertThat(tester.getFirst_name()).isEqualTo(retrievedProfile.getBody().getFirst_name());

		profilereptest.delete(tester);
	}

}
