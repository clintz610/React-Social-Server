package com.revature.follow;

import com.revature.follow.FollowingService;
import com.revature.groups.Group;
import com.revature.users.User;
import com.revature.users.usersettings.UserSettings;
import com.revature.follow.FollowRepository;
import com.revature.users.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestFollowingServices {

    FollowingService sut;

    UserRepository mockUserRepository;
    FollowRepository mockFollowRepository;
    List<Group> groups  = new ArrayList<>();
    List<User> following = new ArrayList<>();
    List<User> follower = new ArrayList<>();
    User currentUser = new User("valid", null, "valid@valid.valid", following, groups, follower);

    @BeforeEach
    public void setUp() {
        mockUserRepository = mock(UserRepository.class);
        mockFollowRepository = mock(FollowRepository.class);
        sut = new FollowingService(mockUserRepository, mockFollowRepository);
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

        Assert.assertEquals(1, result);
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

        Assert.assertEquals(1, result);
    }
}
