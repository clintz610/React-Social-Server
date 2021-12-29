package com.revature.dtos;

import com.revature.models.Group;
import com.revature.models.User;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserDto implements Serializable {

    private final String id;
    private final UserSettingsDto userSettings;
    private final String email;
    private final List<String> followingIds;
    private final List<UUID> groupIds;

    public UserDto(User user){
        this.followingIds = user.getFollowing().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        this.groupIds = user.getGroups().stream()
                .map(Group::getId)
                .collect(Collectors.toList());
        this.email = user.getEmail();
        this.id = user.getId();
        this.userSettings = new UserSettingsDto(user.getUserSettings());
    }
}
