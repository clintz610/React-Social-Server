package com.revature.groups;

import com.revature.exceptions.DuplicateGroupNameException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.follow.FollowRepository;
import com.revature.follow.FollowingService;
import com.revature.groups.dtos.GroupCreationRequest;
import com.revature.users.User;
import com.revature.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestGroupService {

    private GroupRepository mockGroupRepo;
    private UserRepository mockUserRepo;

    private GroupService sut;

    @BeforeEach
    public void setUp() {
        mockUserRepo = mock(UserRepository.class);
        mockGroupRepo = mock(GroupRepository.class);
        sut = new GroupService(mockGroupRepo, mockUserRepo);
    }

    @AfterEach
    public void cleanTestSetup() {
        sut = null;
    }

    @Test
    public void test_createGroup_finishesSuccessfully_givenValidRequest() {

        // Arrange
        GroupCreationRequest validRequest = new GroupCreationRequest();
        validRequest.setDescription("Valid Description");
        validRequest.setName("Valid");

        User validUser = new User();
        validUser.setId(UUID.randomUUID().toString());
        validUser.setEmail("real@email.com");

        when(mockGroupRepo.findGroupByName(validRequest.getName())).thenReturn(Optional.empty());
        when(mockGroupRepo.save(any())).thenReturn(new Group());

        // Act
        sut.createGroup(validRequest, validUser);

        // Assert
        verify(mockGroupRepo, times(1)).findGroupByName(validRequest.getName());
        verify(mockGroupRepo, times(1)).save(any());

    }

    @Test
    public void test_createGroup_throwsInvalidRequestException_givenInvalidName() {

        // Arrange
        GroupCreationRequest invalidRequest_1 = new GroupCreationRequest();
        invalidRequest_1.setDescription("Valid Description");
        invalidRequest_1.setName("");

        GroupCreationRequest invalidRequest_2 = new GroupCreationRequest();
        invalidRequest_2.setDescription("valid Description");
        invalidRequest_2.setName(null);

        User validUser = new User();
        validUser.setId(UUID.randomUUID().toString());
        validUser.setEmail("real@email.com");

        when(mockGroupRepo.findGroupByName(any())).thenReturn(Optional.empty());
        when(mockGroupRepo.save(any())).thenReturn(new Group());

        // Act
        Assertions.assertThrows(
                InvalidRequestException.class,
                () -> sut.createGroup(invalidRequest_1, validUser),
                "Expected Invalid Request Exception to be thrown with Empty String in name");

        Assertions.assertThrows(
                InvalidRequestException.class,
                () -> sut.createGroup(invalidRequest_2, validUser),
                "Expected Invalid Request Exception to be thrown with null value in name");

        // Assert
        verify(mockGroupRepo, times(0)).findGroupByName(any());
        verify(mockGroupRepo, times(0)).save(any());


    }

    @Test
    public void test_createGroup_throwsDuplicateGroupNameException_givenDuplicateGroupName() {

        // Arrange
        GroupCreationRequest invalidRequest = new GroupCreationRequest();
        invalidRequest.setDescription("Valid Description");
        invalidRequest.setName("Duplicate Name");

        User validUser = new User();
        validUser.setId(UUID.randomUUID().toString());
        validUser.setEmail("real@email.com");

        when(mockGroupRepo.findGroupByName(invalidRequest.getName())).thenReturn(Optional.of(new Group()));
        when(mockGroupRepo.save(any())).thenReturn(new Group());

        // Act
        Assertions.assertThrows(
                DuplicateGroupNameException.class,
                () -> sut.createGroup(invalidRequest, validUser),
                "Expected Duplicate Group Name Exception to be thrown with Duplicate Name");

        // Assert
        verify(mockGroupRepo, times(1)).findGroupByName(invalidRequest.getName());
        verify(mockGroupRepo, times(0)).save(any());

    }
}
