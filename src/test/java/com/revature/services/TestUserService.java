package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.ReverbApplication;

@SpringBootTest(classes = ReverbApplication.class)
@RunWith(SpringRunner.class)
public class TestUserService {

	@Autowired
	private MockMvc mvc;
	
	@Mock
	UserRepository testuserrepository;

	@Mock
	private UserService userservicetest;

	// @Test
	// private void registerUserTest() {
	// 	User TestUser = new User("45673yh43yh5vg54h376","Testemail@email.com");
	// 	userservicetest.registerUser(TestUser);

	// 	assertThat(TestUser).isEqualTo(testuserrepository.getById(TestUser.getUid()));

		
	// }
}
