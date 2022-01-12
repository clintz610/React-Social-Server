package com.revature.users.profiles;
import com.revature.exceptions.WrongUserException;
import com.revature.users.User;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.revature.users.dtos.PicUrlDto;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
		Mockito.when(profileRepository.findById(UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"))).thenReturn(Optional.of(profile));
		ProfileService profileService=new ProfileService(profileRepository);
		assertThat(profileService.findProfileById(UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"))).isEqualTo(profile);
	}

	@Test
	public void findProfileByIdNegative()
	{

		Profile profile = new Profile();
		Mockito.when(profileRepository.findById(UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"))).thenReturn(Optional.empty());
		ProfileService profileService=new ProfileService(profileRepository);

		try {
			profileService.findProfileById(UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"));
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
		Mockito.when(profileRepository.findById(UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"))).thenReturn(Optional.of(profile));
		ProfileService profileService=new ProfileService(profileRepository);
		assertThat(profileService.checkProfileOwnership(UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"),u)).isTrue();
	}

	@Test
	public void testUpdateProfileWithWrongUser() throws WrongUserException {
		User u=new User();
		Profile profile=new Profile();
		profile.setUser(u);
		ProfileService profileService=new ProfileService(profileRepository);
		Assertions.assertThrows(WrongUserException.class,()->{profileService.updateProfile(profile,null);});
	}

	@Test
	public void testUpdateProfile() throws WrongUserException {
		User u=new User("d921e5f2-86cb-4a0f-abd9-b9et4aafa3c5","dev4@dev.com", null,null, null);
		Profile profile=new Profile();
		profile.setUser(u);
		Mockito.when(profileRepository.saveAndFlush(profile)).thenReturn(profile);
		ProfileService profileService=new ProfileService(profileRepository);
		boolean testcase1=profileService.updateProfile(profile,u)==profile;
		Assert.assertTrue(testcase1);
		Mockito.verify(profileRepository,times(1)).saveAndFlush(profile);
	}

	@Test
	public void testUpdatePicUrlWithHeader() throws UserNotFoundException {
		User u=new User("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5","dev4@dev.com",null, null,null);
		ProfileService profileService=new ProfileService(profileRepository);
		Profile profile=new Profile();
		profile.setUser(u);
		PicUrlDto test1= profileService.updatePicUrl("header","http://hello.com",UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"),u);
//		PicUrlDto expect=new PicUrlDto(UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"),"http://hello.com");
//		assertTrue(test1==expect);
		verify(profileRepository,times(1)).updateHeaderUrl("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5","http://hello.com");
	}

	@Test
	public void testUpdatePicUrlWithProfile() throws UserNotFoundException {
		User u=new User("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5","dev4@dev.com", null,null, null);
		ProfileService profileService=new ProfileService(profileRepository);
		Profile profile=new Profile();
		profile.setUser(u);
		PicUrlDto test1=profileService.updatePicUrl("profile","http://hello.com",UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"),u);
		verify(profileRepository,times(1)).updatePicUrl("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5","http://hello.com");
	}

	@Test
	public void testFindUsersProfileWithNullUser() throws UserNotFoundException {
		ProfileService profileService=new ProfileService(profileRepository);
		Assertions.assertThrows(UserNotFoundException.class,()->{profileService.findUsersProfile(null);});
	}

	@Test
	public void testCheckProfileOwnershipWithNullUser() throws UserNotFoundException {
		ProfileService profileService=new ProfileService(profileRepository);
		Assertions.assertThrows(UserNotFoundException.class,()->{profileService.checkProfileOwnership(null,new User());});
	}

}