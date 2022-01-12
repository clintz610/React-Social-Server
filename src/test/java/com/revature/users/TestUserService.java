package com.revature.users;

import com.revature.users.profiles.Profile;
import com.revature.users.profiles.ProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestUserService {

    // Initialize repositories to be mocked
    private UserRepository mockUserRepo;
    private ProfileRepository mockProfileRepo;
    private UserService mockService;

    @BeforeEach
    public void setup(){
        mockUserRepo = mock(UserRepository.class);
        mockProfileRepo = mock(ProfileRepository.class);
        mockService = new UserService(mockUserRepo, mockProfileRepo);
    }

    @AfterEach
    public void cleanup(){
        mockService = null;
    }

    @Test
    public void createValidUser(){
        //Arrange
        // Fake user to add to the service.
        User newUser = new User();
        newUser.setEmail("fresh@mail.com");
        newUser.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");

        when(mockUserRepo.save(newUser)).thenReturn(newUser);
        when(mockProfileRepo.save(any())).thenReturn(new Profile());

        //Act
        mockService.registerUser(newUser);

        //Assert
        verify(mockUserRepo, times(1)).save(newUser);
        verify(mockProfileRepo, times(1)).save(any());
    }

    // Login method saves a user if it doesn't exist in the database, since firebase handles the authentication.
    @Test
    public void validLoginDoesNotSaveNewUser(){
        // Arrange
        // Create our user
        User newUser = new User();
        newUser.setEmail("fresh@mail.com");
        newUser.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");

        // When we look for our dear user, we find him as it's valid
        when(mockUserRepo.findById(newUser.getId())).thenReturn(Optional.of(newUser));

        // Act
        mockService.loginUser(newUser);

        // Assert
        verify(mockUserRepo, times(1)).findById(newUser.getId());
        verify(mockUserRepo,times(0)).save(any());
    }

    @Test
    public void validFirebaseLoginNotInDatabaseSavesTheUser(){
        // Arrange
        // Create our user
        User newUser = new User();
        newUser.setEmail("fresh@mail.com");
        newUser.setId("0dVqG3mQr01tWwuIsghJMQm6oZKb");

        // When we look for our dear user, we don't find them
        when(mockUserRepo.findById(newUser.getId())).thenReturn(Optional.<User>empty());

        // Mock a valid save
        when(mockUserRepo.save(any())).thenReturn(newUser);

        // Act
        mockService.loginUser(newUser);

        // Assert
        verify(mockUserRepo, times(1)).findById(newUser.getId());
        verify(mockUserRepo,times(1)).save(any());
    }
}
