package com.revature.follow;

import com.revature.follow.FollowingService;
import com.revature.groups.Group;
import com.revature.users.User;
import com.revature.users.profiles.Profile;
import com.revature.users.profiles.ProfileRepository;
import com.revature.users.profiles.ProfileService;
import com.revature.users.usersettings.UserSettings;
import com.revature.follow.FollowRepository;
import com.revature.users.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
public class TestFollowingServices {

    FollowingService sut;

    UserRepository mockUserRepository;
    FollowRepository mockFollowRepository;
    ProfileRepository mockProfileRepository;
    ProfileService mockProfileService;
    List<Group> groups  = new ArrayList<>();
    List<User> following = new ArrayList<>();
    List<User> follower = new ArrayList<>();
    User currentUser = new User("valid", null, "valid@valid.valid", following, groups, follower);

    @BeforeEach
    public void setUp() {
        mockUserRepository = mock(UserRepository.class);
        mockFollowRepository = mock(FollowRepository.class);
        mockProfileRepository = mock(ProfileRepository.class);
        mockProfileService = mock(ProfileService.class);
        sut = new FollowingService(mockUserRepository, mockFollowRepository, mockProfileRepository, mockProfileService);
    }

    @AfterEach
    public void cleanTestSetup() {
        sut = null;
    }

    @Test
    public void test_Add_New_Follower_To_Current_User() {
    //Assemble
        List<Group> groups  = new ArrayList<>();
        List<User> newFollowing = new ArrayList<>();
        List<User> newFollower = new ArrayList<>();
        User newUser = new User("valid", null, "valid@valid.valid", newFollowing, groups, newFollower);
        UserSettings newUserSettings = new UserSettings("valid", newUser ,false);
        newUser.setUserSettings(newUserSettings);

        when(mockFollowRepository.findAll()).thenReturn(follower);

        follower.add(newUser);
        //Act

        int result = sut.getFollowerNumber(currentUser);

        //Assert

        Assertions.assertEquals(1, result);
    }

    @Test
    public void test_Add_New_Following_To_Current_User() {
        //Assemble
        List<Group> groups  = new ArrayList<>();
        List<User> newFollowing = new ArrayList<>();
        List<User> newFollower = new ArrayList<>();
        User newUser = new User("valid", null, "valid@valid.valid", newFollowing, groups, newFollower);
        UserSettings newUserSettings = new UserSettings("valid", newUser ,false);
        newUser.setUserSettings(newUserSettings);
        when(mockFollowRepository.findAll()).thenReturn(following);
        following.add(newUser);
        //Act

        int result = sut.getFollowingNumber(currentUser);

        //Assert

        Assertions.assertEquals(1, result);
    }

    @Test
    public void test_getUserFromProfile_returnsValidUser_givenValidProfileId(){
        //assemble
        UUID validUUID = UUID.randomUUID();
        User validUser = new User();
        Profile validProfile = new Profile();
        validProfile.setUser(validUser);
        when(mockProfileService.findProfileById(validUUID)).thenReturn(validProfile);

        //act
        User actualUser = sut.getUserFromProfile(validUUID.toString());

        verify(mockProfileService, times(1)).findProfileById(validUUID);
        Assertions.assertEquals(validUser, actualUser);
    }

    @Test
    public void test_followUser_returnsTrue_givenValidUserAndValidFollowUserId() {
        // Arrange
        User validUser = new User();
        validUser.setId(UUID.randomUUID().toString());
        List<User> followingList = new ArrayList<>();
        validUser.setFollowing(followingList);

        String validFollowUserId = UUID.randomUUID().toString();

        User followUser = new User();
        followUser.setId(validFollowUserId);

        when(mockUserRepository.findById(validUser.getId())).thenReturn(Optional.of(validUser));
        when(mockUserRepository.findById(validFollowUserId)).thenReturn(Optional.of(followUser));
        when(mockUserRepository.save(validUser)).thenReturn(validUser);

        // Act
        boolean actualResult = sut.followUser(validUser, validFollowUserId);

        // Assert
        verify(mockUserRepository, times(1)).findById(validUser.getId());
        verify(mockUserRepository, times(1)).findById(validFollowUserId);
        verify(mockUserRepository, times(1)).save(validUser);

        Assertions.assertTrue(actualResult, "Expected to return true");
        Assertions.assertEquals(validUser.getFollowing().size(), 1, "Expected size of following list to increase from 0 to 1");

    }

}
