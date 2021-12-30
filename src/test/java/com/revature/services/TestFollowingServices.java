package com.revature.services;

import com.revature.models.Group;
import com.revature.models.User;
import com.revature.models.UserSettings;
import com.revature.repositories.FollowRepository;
import com.revature.repositories.UserRepository;
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
        List<User> following = new ArrayList<>();
        List<User> follower = new ArrayList<>();
        User newUser = new User("valid", null, "valid@valid.valid", following, groups, follower);
        UserSettings newUserSettings = new UserSettings("valid", newUser ,false);
        newUser.setUserSettings(newUserSettings);

        when(mockFollowRepository.findAll()).thenReturn(follower);

        follower.add(newUser);
        //Act

        int result = sut.getFollowings().size();

        //Assert

        Assert.assertEquals(1, result);
    }

    @Test
    public void test_Add_New_Following_To_Current_User() {
        //Assemble
        List<Group> groups  = new ArrayList<>();
        List<User> following = new ArrayList<>();
        List<User> follower = new ArrayList<>();
        User newUser = new User("valid", null, "valid@valid.valid", following, groups, follower);
        UserSettings newUserSettings = new UserSettings("valid", newUser ,false);
        newUser.setUserSettings(newUserSettings);

        when(mockFollowRepository.findAll()).thenReturn(following);

        following.add(newUser);
        //Act

        int result = sut.getFollowers().size();

        //Assert

        Assert.assertEquals(1, result);
    }
}
