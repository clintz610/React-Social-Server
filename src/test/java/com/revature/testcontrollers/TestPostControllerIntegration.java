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
import com.revature.controllers.PostController;
import com.revature.controllers.ProfileController;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestPostControllerIntegration {

	@Autowired
	private PostController prostctrl;
	
	@Autowired
	private UserService userservice;

	 @Test
	 public void getPostsTest() throws Exception {
		
		 assertThat(prostctrl.getPosts().getStatusCode().compareTo(HttpStatus.OK));

	 }
	
//	 @Test
//	 public void submitPostTest() throws Exception {
//		 User testuser6 = new User("587722673987a6svf9","76876v@email.com");
//		 Post post = new Post();
//		 assertThat(prostctrl.getPosts().getStatusCode().compareTo(HttpStatus.OK));
//
//	 }
}
