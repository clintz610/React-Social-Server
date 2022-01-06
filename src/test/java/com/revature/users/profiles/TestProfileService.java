package com.revature.users.profiles;
import com.revature.users.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;
import com.revature.exceptions.UserNotFoundException;


@SpringBootTest(classes = ReverbApplication.class)
@ActiveProfiles("test")
public class TestProfileService 
{
	

	private ProfileRepository profileRepository;

	@BeforeEach
	public void setup()
	{
		//mocks the repositories for each test
		profileRepository = Mockito.mock(ProfileRepository.class);
		
	}
	
	@Test
	public void findProfileByIdPositive() throws UserNotFoundException {
		Profile profile = new Profile();
		Mockito.when(profileRepository.findById("1")).thenReturn(Optional.of(profile));
		ProfileService profileService=new ProfileService(profileRepository);
		assertThat(profileService.findProfileById("1")).isEqualTo(profile);
	}

	@Test
	public void findProfileByIdNegative() 
	{
		
		Profile profile = new Profile();
		Mockito.when(profileRepository.findById("1")).thenReturn(Optional.empty());
		ProfileService profileService=new ProfileService(profileRepository);
		
		try {
			profileService.findProfileById("1");
			fail();
		} catch (Exception e) {
			assertEquals(e.getClass(), UserNotFoundException.class);
		}
	}

	@Test
	public void findProfileByUser() throws UserNotFoundException {
		User u=new User();
		Profile profile = new Profile();
		Mockito.when(profileRepository.getProfileByUser(u)).thenReturn(Optional.of(profile));
		ProfileService profileService=new ProfileService(profileRepository);
		assertThat(profileService.findUsersProfile(u)).isEqualTo(profile);
	}

	@Test
	public void checkProfileOwner() throws UserNotFoundException {
		User u=new User();
		Profile profile = new Profile();
		profile.setUser(u);
		Mockito.when(profileRepository.findById("1")).thenReturn(Optional.of(profile));
		ProfileService profileService=new ProfileService(profileRepository);
		assertThat(profileService.checkProfileOwnership("1",u)).isTrue();
	}

}
