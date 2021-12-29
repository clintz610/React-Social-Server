package com.revature.testcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;
import com.revature.controllers.PostSearchController;
import com.revature.models.User;
import com.revature.services.UserService;

@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestsearchByUserIntegration {
    /*

	@Autowired
	private PostSearchController postsearchctrl;
	
	@Autowired
	private UserService userservice;
	
	 @Test
	 public void searchByUserTest() throws Exception {
		 
		 User testuser = new User("58235625gfs6987vf9","76634v68@email.com");
		 
		 userservice.registerUser(testuser);
		 		
		 assertThat(postsearchctrl.searchByUser(testuser).getBody().size()).isEqualTo(0);
	 	
	 	
	}

     */
	
}
