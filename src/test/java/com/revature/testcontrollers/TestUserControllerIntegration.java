package com.revature.testcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;
import com.revature.controllers.UserController;
import com.revature.models.User;

@SpringBootTest(classes = ReverbApplication.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class TestUserControllerIntegration {
    /*
	
	@Autowired
	private UserController userctrl;

	 @Test
	 public void loginUserTest() throws Exception {
		 
		 User testuser = new User("587722673625gfs78dv6987a6svf9","76876v8g7v68@email.com");
		 
		 assertThat((userctrl.loginUser(testuser)).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	 }
	 
	 @Test
	 public void registerUserTest() throws Exception {
		 
		 User testuser = new User("587722673625g56fs78dv6987a6svf9","76876v8g756v68@email.com");
		 
		 assertThat(userctrl.registerUser(testuser).getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	 }

     */
}