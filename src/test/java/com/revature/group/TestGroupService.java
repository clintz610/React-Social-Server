package com.revature.group;

import com.revature.exceptions.GroupNotFoundException;
import com.revature.groups.Group;
import com.revature.groups.GroupRepository;
import com.revature.groups.GroupService;
import com.revature.groups.dtos.GroupResponse;
import com.revature.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
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
        when(mockGroupRepo.findGroupByName(groupName)).thenReturn(java.util.Optional.of(group));
        GroupResponse expectedResult = new GroupResponse(group);

        GroupResponse actualResult = sut.getGroup(groupName);

        Assertions.assertEquals(expectedResult, actualResult, "Expected for group to be the same but they were not");
    }

    @Test
    public void test_getGroup_throwsGroupNotFoundException_givenUnusedGroupName() {
        GroupNotFoundException thrown = Assertions.assertThrows(GroupNotFoundException.class, () -> sut.getGroup("unused name"), "Expected to find no group but found a group");

        Assertions.assertTrue(thrown.getMessage().contains("Supplied group name was not found"));
    }



}
