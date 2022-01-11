package com.revature.groups;

import com.revature.exceptions.*;
import com.revature.groups.dtos.GroupCreationRequest;
import com.revature.groups.dtos.GroupResponse;
import com.revature.groups.dtos.GroupUpdateRequest;
import com.revature.users.User;
import com.revature.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    Predicate<String> notNullOrEmpty = str -> str != null && !str.equals("");

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    /**
     * @return Returns all groups in database
     */
    public List<GroupResponse> getAllGroups() {
        List<GroupResponse> groupResponseList = new ArrayList<>();

        groupRepository.findAll().iterator()
                .forEachRemaining(g -> groupResponseList.add(new GroupResponse(g)));

        return groupResponseList;
    }

    /**
     * @param groupName - name of group to be fetched
     * @return - Response entity representing group
     */
    public GroupResponse getGroup(String groupName) {
        Group group = groupRepository.findGroupByName(groupName)
                                     .orElseThrow(GroupNotFoundException::new);
        return new GroupResponse(group);
    }

    /**
     * @param groupCreationRequest - contains Group Name and Description of new group.
     * @param owner - Currently logged in user gets set as owner.
     */
    public GroupResponse createGroup(GroupCreationRequest groupCreationRequest, User owner) {

        if (!notNullOrEmpty.test(groupCreationRequest.getName()))
            throw new InvalidRequestException("Invalid group name entered");

        if (groupRepository.findGroupByName(groupCreationRequest.getName()).isPresent())
            throw new DuplicateGroupNameException();

        Group newGroup = new Group();

        newGroup.setOwner(owner);
        //TODO: sidenote, do we want to trim here as well? UI already does, not necessary here
        newGroup.setName(groupCreationRequest.getName().trim());
        newGroup.setDescription(groupCreationRequest.getDescription());
        List<User> list = new ArrayList<>();
        list.add(userRepository.findUserByEmail(owner.getEmail()).get());
        newGroup.setUsers(list);

        return new GroupResponse(groupRepository.save(newGroup));
    }

    /**
     * @param groupName - Name of group to be joined.
     * @param currUser - currently logged in user.
     */
    public void joinGroup(String groupName, User currUser) {
        Group group = groupRepository.findGroupByName(groupName).get();

        if (group.getUsers().contains(currUser))
            throw new DuplicateRequestException("User has already joined the group");

        group.getUsers().add(currUser);

        groupRepository.save(group);
    }

    /**
     * @param groupName - Name of group to be left.
     * @param currUser - currently logged in user
     */
    public void leaveGroup(String groupName, User currUser) {
        Group group = groupRepository.findGroupByName(groupName).get();

        if (!group.getUsers().contains(currUser))
            throw new DuplicateRequestException("User is not in the group");

        group.getUsers().remove(currUser);

        groupRepository.save(group);
    }

    /**
     * updates a selected group with given values, or if empty
     * do not update field, except description can be empty.
     *
     * @param currentGroupName - name of group to be edited
     * @param updateReq - fields to be updated, or null
     */
    public GroupResponse updateGroup(String currentGroupName, GroupUpdateRequest updateReq, User currUser) {
        Group group = groupRepository.findGroupByName(currentGroupName).orElseThrow(GroupNotFoundException::new);

        if (!group.getOwner().equals(currUser))
            throw new UnauthorizedRequestException("Logged in user is not owner of this group, could not update");

        if (notNullOrEmpty.test(updateReq.getOwnerEmail())) {
            User newOwner = userRepository.findUserByEmail(updateReq.getOwnerEmail()).orElseThrow(UserNotFoundException::new);

            if (group.getUsers().contains(newOwner)) group.setOwner(newOwner);
            else throw new UserNotFoundException("User is not in Group");
        }

        if (notNullOrEmpty.test(updateReq.getName())) {
            if(!updateReq.getName().equals(currentGroupName) && groupRepository.findGroupByName(updateReq.getName()).isPresent())
                throw new DuplicateGroupNameException();
            group.setName(updateReq.getName().trim());
        }

        group.setDescription(updateReq.getDescription());

        if (notNullOrEmpty.test(updateReq.getHeaderImg())) {
            group.setHeaderImg(updateReq.getHeaderImg());
        }

        if(notNullOrEmpty.test(updateReq.getProfilePic()))
            group.setProfilePic(updateReq.getProfilePic());

        return new GroupResponse(groupRepository.save(group));
    }

    /**
     * @param groupName - name of group to be deleted
     * @param currUser - currently logged in user
     */
    public void deleteGroup(String groupName, User currUser) {
        Group group = groupRepository.findGroupByName(groupName).get();

        if (group.getOwner().equals(currUser))
            groupRepository.delete(group);
        else throw new UnauthorizedRequestException("Logged in user is not owner of this group, could not delete");
    }

}
