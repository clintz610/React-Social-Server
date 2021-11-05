package com.revature.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.revature.models.Profile;
import com.revature.repositories.ProfileRepository;
import com.revature.ReverbApplication;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
//properties = {"server.port=8081",
//		"management.server.port=8082"})
@SpringBootTest(classes = ReverbApplication.class)
public class TestControllerIntegration {

	@Autowired
	private ProfileController profilectrl;

	@Autowired
	private ProfileRepository profilereptest;

	@Test
	public void devops_profile_test() throws Exception {

		Profile tester = new Profile();

		profilereptest.save(tester);

		ResponseEntity<Profile> retrievedProfile = profilectrl.findProfileById(tester.getId());

		assertThat(tester.getFirst_name()).isEqualTo(retrievedProfile.getBody().getFirst_name());


	}

}
