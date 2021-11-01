package com.revature.controllers;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.models.Profile;
import com.revature.ReverbApplication;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
//properties = {"server.port=8081",
//		"management.server.port=8082"})
@SpringBootTest(classes = ReverbApplication.class)
@ActiveProfiles("reverb-backend-integration-test")
public class TestControllerIntegration {
	
	@Autowired
	private ProfileController profilecontroller;
	
	@Test
	public void devops_profile_test() throws Exception{
		
		
		Profile tester = new Profile( 1,
	     "first_name",
	     "last_name",
	     "profile_img",
	     "header_img",
	     "about_me" );
	
		
		
	}
		
		
}
