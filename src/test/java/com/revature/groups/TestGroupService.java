package com.revature.groups;

import com.revature.exceptions.*;
import com.revature.groups.dtos.GroupCreationRequest;
import com.revature.groups.dtos.GroupResponse;
import com.revature.groups.dtos.GroupUpdateRequest;
import com.revature.users.User;
import com.revature.users.UserRepository;
import com.revature.users.usersettings.UserSettings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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
    public void test_getAllGroups_returnsAllGroupsInDatabase() {
        Group group = new Group();
        group.setUsers(new ArrayList<>());
        Group[] test = {group};
        Iterable<Group> dummyResult = Arrays.asList(test);
        when(mockGroupRepo.findAll()).thenReturn(dummyResult);
        List<GroupResponse> expectedList = new ArrayList<>();
        dummyResult.iterator().forEachRemaining(g -> expectedList.add(new GroupResponse(g)));

        List<GroupResponse> actualList = sut.getAllGroups();

        Assertions.assertEquals(expectedList, actualList, "Expected for list to be the same but they were not");
    }

    @Test
    public void test_getGroup_returnsGroupInDatabase_givenGroupName() {
        Group group = new Group();
        group.setUsers(new ArrayList<>());
        String groupName = "test";
        group.setName(groupName);
        when(mockGroupRepo.findGroupByName(groupName)).thenReturn(Optional.of(group));
        GroupResponse expectedResult = new GroupResponse(group);

        GroupResponse actualResult = sut.getGroup(groupName);

        Assertions.assertEquals(expectedResult, actualResult, "Expected for group to be the same but they were not");
    }

    @Test
    public void test_getGroup_throwsGroupNotFoundException_givenUnusedGroupName() {

        when(mockGroupRepo.findGroupByName("unused name")).thenReturn(Optional.empty());

        GroupNotFoundException thrown = Assertions.assertThrows(GroupNotFoundException.class, () -> sut.getGroup("unused name"), "Expected to find no group but found a group");

        Assertions.assertTrue(thrown.getMessage().contains("Supplied group name was not found"));
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

    @Test
    public void test_joinGroup_completesSuccessfully_givenUserIsNotInGroup() {

        // Arrange
        UUID id = UUID.randomUUID();

        String validGroupName = "Group";

        User joiningUser = new User();
        joiningUser.setId(id.toString());
        joiningUser.setEmail("email@mail.com");
        joiningUser.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();

        Group foundGroup = new Group();
        foundGroup.setName("Group");
        foundGroup.setDescription("I am Group");
        foundGroup.setProfilePic("Valid");
        foundGroup.setHeaderImg("Valid");
        foundGroup.setUsers(joinedUsers);

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(foundGroup));
        when(mockGroupRepo.save(foundGroup)).thenReturn(foundGroup);

        // Act
        sut.joinGroup(validGroupName, joiningUser);

        // Assert
        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockGroupRepo, times(1)).save(foundGroup);

        Assertions.assertEquals(1, foundGroup.getUsers().size(), "Expected Length of List to increase by 1");
    }

    @Test
    public void test_joinGroup_throwsDuplicateRequestException_givenUserIsInGroup() {

        // Arrange
        UUID id = UUID.randomUUID();

        String validGroupName = "Group";

        User joinedUser = new User();
        joinedUser.setId(id.toString());
        joinedUser.setEmail("email@mail.com");
        joinedUser.setUserSettings(new UserSettings());

        User joiningUser = new User();
        joiningUser.setId(id.toString());
        joiningUser.setEmail("email@mail.com");
        joiningUser.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(joinedUser);

        Group foundGroup = new Group();
        foundGroup.setName("Group");
        foundGroup.setDescription("I am Group");
        foundGroup.setProfilePic("Valid");
        foundGroup.setHeaderImg("Valid");
        foundGroup.setUsers(joinedUsers);

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(foundGroup));
        when(mockGroupRepo.save(foundGroup)).thenReturn(foundGroup);

        // Act
        Assertions.assertThrows(
                DuplicateRequestException.class,
                () -> sut.joinGroup(validGroupName, joiningUser),
                "Expected Duplicate Request Exception to be thrown when User has already joined group");

        // Assert
        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockGroupRepo, times(0)).save(foundGroup);

        Assertions.assertEquals(1, foundGroup.getUsers().size(), "Expected Length of List to remain at 1");

    }

    @Test
    public void test_leaveGroup_completesSuccessfully_givenUserIsInGroup() {

        // Arrange
        UUID id = UUID.randomUUID();

        String validGroupName = "Group";

        User joinedUser = new User();
        joinedUser.setId(id.toString());
        joinedUser.setEmail("email@mail.com");
        joinedUser.setUserSettings(new UserSettings());

        User leavingUser = new User();
        leavingUser.setId(id.toString());
        leavingUser.setEmail("email@mail.com");
        leavingUser.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(joinedUser);

        Group foundGroup = new Group();
        foundGroup.setName("Group");
        foundGroup.setDescription("I am Group");
        foundGroup.setProfilePic("Valid");
        foundGroup.setHeaderImg("Valid");
        foundGroup.setUsers(joinedUsers);

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(foundGroup));
        when(mockGroupRepo.save(foundGroup)).thenReturn(foundGroup);

        // Act
        sut.leaveGroup(validGroupName, leavingUser);

        // Assert
        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockGroupRepo, times(1)).save(foundGroup);

        Assertions.assertEquals(0, foundGroup.getUsers().size(), "Expected Length of List to go down to 0");
    }

    @Test
    public void test_leaveGroup_throwsDuplicateRequestException_givenUserIsNotInGroup() {

        // Arrange
        UUID id = UUID.randomUUID();

        String validGroupName = "Group";

        User leavingUser = new User();
        leavingUser.setId(id.toString());
        leavingUser.setEmail("email@mail.com");
        leavingUser.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();

        Group foundGroup = new Group();
        foundGroup.setName("Group");
        foundGroup.setDescription("I am Group");
        foundGroup.setProfilePic("Valid");
        foundGroup.setHeaderImg("Valid");
        foundGroup.setUsers(joinedUsers);

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(foundGroup));
        when(mockGroupRepo.save(foundGroup)).thenReturn(foundGroup);

        // Act
        Assertions.assertThrows(
                DuplicateRequestException.class,
                () -> sut.leaveGroup(validGroupName, leavingUser),
                "Expected Duplicate Request Exception to be thrown when User is not in group");

        // Assert
        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockGroupRepo, times(0)).save(foundGroup);

        Assertions.assertEquals(0, foundGroup.getUsers().size(), "Expected Length of List to remain at 0");

    }

    @Test
    public void test_deleteGroup_completesSuccessfully_givenUserIsGroupOwner() {

        // Arrange
        UUID id = UUID.randomUUID();

        String validGroupName = "Group";

        User currentOwner = new User();
        currentOwner.setId(id.toString());
        currentOwner.setEmail("email@mail.com");
        currentOwner.setUserSettings(new UserSettings());

        User deletingUser = new User();
        deletingUser.setId(id.toString());
        deletingUser.setEmail("email@mail.com");
        deletingUser.setUserSettings(new UserSettings());

        Group foundGroup = new Group();
        foundGroup.setName("Group");
        foundGroup.setDescription("I am Group");
        foundGroup.setProfilePic("Valid");
        foundGroup.setHeaderImg("Valid");
        foundGroup.setOwner(currentOwner);

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(foundGroup));
        doNothing().when(mockGroupRepo).delete(foundGroup);

        // Act
        sut.deleteGroup(validGroupName, deletingUser);

        // Assert
        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockGroupRepo, times(1)).delete(foundGroup);

    }

    @Test
    public void test_deleteGroup_throwsUnauthorizedRequestException_givenUserIsNotGroupOwner() {

        // Arrange
        UUID id_1 = UUID.randomUUID();
        UUID id_2 = UUID.randomUUID();

        String validGroupName = "Group";

        User currentOwner = new User();
        currentOwner.setId(id_1.toString());
        currentOwner.setEmail("email@mail.com");
        currentOwner.setUserSettings(new UserSettings());

        User deletingUser = new User();
        deletingUser.setId(id_2.toString());
        deletingUser.setEmail("email1@mail.com");
        deletingUser.setUserSettings(new UserSettings());

        Group foundGroup = new Group();
        foundGroup.setName("Group");
        foundGroup.setDescription("I am Group");
        foundGroup.setProfilePic("Valid");
        foundGroup.setHeaderImg("Valid");
        foundGroup.setOwner(currentOwner);

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(foundGroup));
        doNothing().when(mockGroupRepo).delete(foundGroup);

        // Act
        Assertions.assertThrows(
                UnauthorizedRequestException.class,
                () -> sut.deleteGroup(validGroupName, deletingUser),
                "Expected Unauthorized Request Exception to be thrown when User is not owner of group");

        // Assert
        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockGroupRepo, times(0)).delete(foundGroup);

    }

    @Test
    public void test_updateGroup_updatesOwner_givenValidEmail() {

        // Arrange
        UUID id_1 = UUID.randomUUID();
        UUID id_2 = UUID.randomUUID();

        String validGroupName = "Group";

        User currentOwner = new User();
        currentOwner.setId(id_1.toString());
        currentOwner.setEmail("email@mail.com");
        currentOwner.setUserSettings(new UserSettings());

        User newOwner = new User();
        newOwner.setId(id_2.toString());
        newOwner.setEmail("email2@mail.com");
        newOwner.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(currentOwner);
        joinedUsers.add(newOwner);

        Group validGroup = new Group();
        validGroup.setName("Group");
        validGroup.setDescription("I am Group");
        validGroup.setProfilePic("Valid");
        validGroup.setHeaderImg("Valid");
        validGroup.setOwner(currentOwner);
        validGroup.setUsers(joinedUsers);

        GroupUpdateRequest validGroupUpdate = new GroupUpdateRequest();
        validGroupUpdate.setDescription("I am Group");
        validGroupUpdate.setOwnerEmail("email2@mail.com");

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(validGroup));
        when(mockUserRepo.findUserByEmail(newOwner.getEmail())).thenReturn(Optional.of(newOwner));
        when(mockGroupRepo.save(validGroup)).thenReturn(validGroup);

        // Act
        sut.updateGroup(validGroupName, validGroupUpdate, currentOwner);

        // Assert
        Assertions.assertEquals(validGroup.getOwner(), newOwner, "Expected Owner to be set to new owner");

        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockUserRepo, times(1)).findUserByEmail(newOwner.getEmail());
        verify(mockGroupRepo, times(1)).save(validGroup);

    }

    @Test
    public void test_updateGroup_updatesName_givenValidName() {

        // Arrange
        UUID id_1 = UUID.randomUUID();

        String validGroupName = "Group";

        User currentOwner = new User();
        currentOwner.setId(id_1.toString());
        currentOwner.setEmail("email@mail.com");
        currentOwner.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(currentOwner);

        Group validGroup = new Group();
        validGroup.setName("Group");
        validGroup.setDescription("I am Group");
        validGroup.setProfilePic("Valid");
        validGroup.setHeaderImg("Valid");
        validGroup.setOwner(currentOwner);

        GroupUpdateRequest validGroupUpdate = new GroupUpdateRequest();
        validGroupUpdate.setDescription("I am Group");
        validGroupUpdate.setName("Group2");

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(validGroup));
        when(mockGroupRepo.findGroupByName(validGroupUpdate.getName())).thenReturn(Optional.empty());
        when(mockUserRepo.findUserByEmail(any())).thenReturn(Optional.of(currentOwner));
        when(mockGroupRepo.save(validGroup)).thenReturn(validGroup);

        // Act
        sut.updateGroup(validGroupName, validGroupUpdate, currentOwner);

        // Assert
        Assertions.assertEquals(validGroup.getName(), validGroupUpdate.getName(), "Expected Name to be set to Group2");

        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockGroupRepo, times(1)).findGroupByName(validGroupUpdate.getName());
        verify(mockUserRepo, times(0)).findUserByEmail(any());
        verify(mockGroupRepo, times(1)).save(validGroup);

    }

    @Test
    public void test_updateGroup_updatesDescription_givenValidValue() {

        // Arrange
        UUID id_1 = UUID.randomUUID();

        String validGroupName = "Group";

        User currentOwner = new User();
        currentOwner.setId(id_1.toString());
        currentOwner.setEmail("email@mail.com");
        currentOwner.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(currentOwner);

        Group validGroup = new Group();
        validGroup.setName("Group");
        validGroup.setDescription("I am Group");
        validGroup.setProfilePic("Valid");
        validGroup.setHeaderImg("Valid");
        validGroup.setOwner(currentOwner);

        GroupUpdateRequest validGroupUpdate = new GroupUpdateRequest();
        validGroupUpdate.setDescription("I am New Group");

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(validGroup));
        when(mockUserRepo.findUserByEmail(any())).thenReturn(Optional.of(currentOwner));
        when(mockGroupRepo.save(validGroup)).thenReturn(validGroup);

        // Act
        sut.updateGroup(validGroupName, validGroupUpdate, currentOwner);

        // Assert
        Assertions.assertEquals(validGroup.getDescription(), validGroupUpdate.getDescription(), "Expected Description to be set to I am New Group");

        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockUserRepo, times(0)).findUserByEmail(any());
        verify(mockGroupRepo, times(1)).save(validGroup);

    }

    @Test
    public void test_updateGroup_updatesHeaderImg_givenValidValue() {

        // Arrange
        UUID id_1 = UUID.randomUUID();

        String validGroupName = "Group";

        User currentOwner = new User();
        currentOwner.setId(id_1.toString());
        currentOwner.setEmail("email@mail.com");
        currentOwner.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(currentOwner);

        Group validGroup = new Group();
        validGroup.setName("Group");
        validGroup.setDescription("I am Group");
        validGroup.setProfilePic("Valid");
        validGroup.setHeaderImg("Valid");
        validGroup.setOwner(currentOwner);

        GroupUpdateRequest validGroupUpdate = new GroupUpdateRequest();
        validGroupUpdate.setDescription("I am Group");
        validGroupUpdate.setHeaderImg("newImg");

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(validGroup));
        when(mockUserRepo.findUserByEmail(any())).thenReturn(Optional.of(currentOwner));
        when(mockGroupRepo.save(validGroup)).thenReturn(validGroup);

        // Act
        sut.updateGroup(validGroupName, validGroupUpdate, currentOwner);

        // Assert
        Assertions.assertEquals(validGroup.getHeaderImg(), validGroupUpdate.getHeaderImg(), "Expected headerImg to be set to newImg");

        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockUserRepo, times(0)).findUserByEmail(any());
        verify(mockGroupRepo, times(1)).save(validGroup);

    }

    @Test
    public void test_updateGroup_updatesProfilePic_givenValidValue() {

        // Arrange
        UUID id_1 = UUID.randomUUID();

        String validGroupName = "Group";

        User currentOwner = new User();
        currentOwner.setId(id_1.toString());
        currentOwner.setEmail("email@mail.com");
        currentOwner.setUserSettings(new UserSettings());

        ArrayList<User> joinedUsers = new ArrayList<>();
        joinedUsers.add(currentOwner);

        Group validGroup = new Group();
        validGroup.setName("Group");
        validGroup.setDescription("I am Group");
        validGroup.setProfilePic("Valid");
        validGroup.setHeaderImg("Valid");
        validGroup.setOwner(currentOwner);

        GroupUpdateRequest validGroupUpdate = new GroupUpdateRequest();
        validGroupUpdate.setDescription("I am Group");
        validGroupUpdate.setProfilePic("newImg");

        when(mockGroupRepo.findGroupByName(validGroupName)).thenReturn(Optional.of(validGroup));
        when(mockUserRepo.findUserByEmail(any())).thenReturn(Optional.of(currentOwner));
        when(mockGroupRepo.save(validGroup)).thenReturn(validGroup);

        // Act
        sut.updateGroup(validGroupName, validGroupUpdate, currentOwner);

        // Assert
        Assertions.assertEquals(validGroup.getProfilePic(), validGroupUpdate.getProfilePic(), "Expected profilePic to be set to newImg");

        verify(mockGroupRepo, times(1)).findGroupByName(validGroupName);
        verify(mockUserRepo, times(0)).findUserByEmail(any());
        verify(mockGroupRepo, times(1)).save(validGroup);

    }

    @Test
    public void test_updateGroup_throwsGroupNotFoundException_givenGroupThatDoesNotExist() {
        String currentGroupName = "unused name";

        when(mockGroupRepo.findGroupByName(currentGroupName)).thenReturn(Optional.empty());

        Assertions.assertThrows(GroupNotFoundException.class, () -> sut.updateGroup(currentGroupName, new GroupUpdateRequest(), new User()));
    }

    @Test
    public void test_updateGroup_throwsUnauthorizedRequestException_givenUserThatIsNotOwnerOfGroup() {
        String currentGroupName = "Used Name";
        Group group = new Group();
        User owner = new User();
        owner.setEmail("test@test.com");
        group.setOwner(owner);
        User notOwner = new User();

        when(mockGroupRepo.findGroupByName(currentGroupName)).thenReturn(Optional.of(group));

        Assertions.assertThrows(UnauthorizedRequestException.class, () -> sut.updateGroup(currentGroupName, new GroupUpdateRequest(), notOwner));
    }

    @Test
    public void test_updateGroup_throwsUsedNotFoundException_givenUserThatDoesNotExist() {
        String currentGroupName = "Used Name";
        Group group = new Group();
        User owner = new User();
        owner.setEmail("test@test.com");
        group.setOwner(owner);
        GroupUpdateRequest notExist = new GroupUpdateRequest();
        notExist.setOwnerEmail("notexisting@test.com");

        when(mockGroupRepo.findGroupByName(currentGroupName)).thenReturn(Optional.of(group));
        when(mockUserRepo.findUserByEmail(notExist.getOwnerEmail())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> sut.updateGroup(currentGroupName, notExist, owner));
    }

    @Test
    public void test_updateGroup_throwsUsedNotFoundException_givenUserThatIsNotInTheGroup() {
        String currentGroupName = "Used Name";
        Group group = new Group();
        User owner = new User();
        owner.setEmail("test@test.com");
        group.setOwner(owner);
        group.setUsers(new ArrayList<>());
        GroupUpdateRequest notInGroup = new GroupUpdateRequest();
        notInGroup.setOwnerEmail("notexisting@test.com");
        User userNotInGroup = new User();
        userNotInGroup.setEmail(notInGroup.getOwnerEmail());

        when(mockGroupRepo.findGroupByName(currentGroupName)).thenReturn(Optional.of(group));
        when(mockUserRepo.findUserByEmail(notInGroup.getOwnerEmail())).thenReturn(Optional.of(userNotInGroup));

        Assertions.assertThrows(UserNotFoundException.class, () -> sut.updateGroup(currentGroupName, notInGroup, owner));
    }

    @Test
    public void test_updateGroup_throwsDuplicateGroupNameException_givenGroupNameThatIsUsedAlready() {
        String currentGroupName = "Used Name";
        Group group = new Group();
        Group usedGroup = new Group();
        User owner = new User();
        User userInGroup = new User();
        owner.setEmail("test@test.com");
        group.setOwner(owner);
        group.setUsers(new ArrayList<>());
        group.getUsers().add(userInGroup);
        usedGroup.setName("Used Group Name");
        GroupUpdateRequest groupNameInUseAlready = new GroupUpdateRequest();
        groupNameInUseAlready.setName("Used Group Name");

        when(mockGroupRepo.findGroupByName(currentGroupName)).thenReturn(Optional.of(group));
        when(mockGroupRepo.findGroupByName(groupNameInUseAlready.getName())).thenReturn(Optional.of(usedGroup));

        Assertions.assertThrows(DuplicateGroupNameException.class, () -> sut.updateGroup(currentGroupName, groupNameInUseAlready, owner));
        verify(mockUserRepo, times(0)).findUserByEmail(any());
    }

}
