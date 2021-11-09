package com.revature.services;
import com.revature.models.Comment;
import com.revature.models.Like;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.repositories.LikeRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.ProfileRepository;
import com.revature.repositories.UserRepository;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.assertj.core.api.Fail;
import org.hibernate.annotations.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.ReverbApplication;
import com.revature.exceptions.PostNotFoundException;
import com.revature.exceptions.ProfileNotFoundException;


@SpringBootTest(classes = ReverbApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TestProfileService 
{
	

	private ProfileRepository profileRepository;

	@BeforeEach
	public void Setup() 
	{
		//mocks the repositories for each test
		profileRepository = Mockito.mock(ProfileRepository.class);
		
	}
	
	@Test
	public void findProfileByIdPositive() throws Exception
	{
		Profile profile = new Profile();
		Mockito.when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
		ProfileService profileService=new ProfileService(profileRepository);
		assertThat(profileService.findProfileById(1)).isEqualTo(profile);
	}

	@Test
	public void findProfileByIdNegative() 
	{
		
		Profile profile = new Profile();
		Mockito.when(profileRepository.findById(1)).thenReturn(Optional.empty());
		ProfileService profileService=new ProfileService(profileRepository);
		
		try {
			profileService.findProfileById(1);
			fail();
		} catch (Exception e) {
			assertEquals(e.getClass(), ProfileNotFoundException.class);
		}
	}

	@Test
	public void findProfileByUser() throws Exception
	{
		User u=new User();
		Profile profile = new Profile();
		Mockito.when(profileRepository.getProfileByUser(u)).thenReturn(Optional.of(profile));
		ProfileService profileService=new ProfileService(profileRepository);
		assertThat(profileService.findUsersProfile(u)).isEqualTo(profile);
	}

	@Test
	public void checkProfileOwner() throws Exception
	{
		User u=new User();
		Profile profile = new Profile();
		profile.setUser(u);
		Mockito.when(profileRepository.findById(1)).thenReturn(Optional.of(profile));
		ProfileService profileService=new ProfileService(profileRepository);
		assertThat(profileService.checkProfileOwnership(1,u)).isTrue();
	}
	

	
}
