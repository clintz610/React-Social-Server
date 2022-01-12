package com.revature.search;

import com.revature.search.dtos.SearchResponse;
import com.revature.search.dtos.SearchResponse.EntitySearchResult;
import com.revature.users.usersettings.UserSettings;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.revature.users.User;
import com.revature.groups.Group;
import com.revature.users.UserRepository;
import com.revature.groups.GroupRepository;


public class TestSearchService {

    private UserRepository mockUserRepository;
    private GroupRepository mockGroupRepository;
    private SearchService sut;

    private List<User> emptyUserList = new ArrayList<>();
    private List<Group> emptyGroupList = new ArrayList<>();
    private List<Searchable> searchableList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        mockUserRepository = mock(UserRepository.class);
        mockGroupRepository = mock(GroupRepository.class);
        sut = new SearchService(mockUserRepository, mockGroupRepository);

        searchableList.add(new EntitySearchResult("7b98dd93-e3e7-4378-8722-b455e1c22698", "First Key"));
        searchableList.add(new EntitySearchResult("9f6b79ae-c897-49cf-a4fe-480fb3b2df92", "Second Key"));
    }

    @AfterEach
    public void cleanTestSetup() { sut = null; }

    @Test
    public void test_userQuery_returnsListOfUsers_givenStringInput() {
        when(mockUserRepository.findByEmailContains(any())).thenReturn(searchableList);
        SearchResponse resultsList = sut.userQuery("test@user.email");

        assertTrue(resultsList.getResponses().size() > 0);
    }

    @Test
    public void test_userQuery_returnsEmptyList_givenEmptyInput() {
        when(mockUserRepository.findByEmailContains(any())).thenReturn(new ArrayList<>());
        SearchResponse resultsList = sut.userQuery("");

        assertTrue(resultsList.getResponses().size() == 0);
    }

    @Test
    public void test_groupQuery_returnsListOfGroups_givenStringInput() {
        when(mockGroupRepository.findByNameContains(any())).thenReturn(searchableList);
        SearchResponse resultsList = sut.groupQuery("Group Name");

        assertTrue(resultsList.getResponses().size() > 0);
    }

    @Test
    public void test_groupQuery_returnsEmptyList_givenEmptyInput() {
        when(mockGroupRepository.findByNameContains(any())).thenReturn(new ArrayList<>());
        SearchResponse resultsList = sut.groupQuery("");

        assertTrue(resultsList.getResponses().size() == 0);
    }

//    @Test
//    public void test_query2_searchForUsers_returnsListOfUsers_givenStringInput() {
//
//    }
}