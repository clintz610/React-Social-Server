package com.revature.groups.dtos;

import lombok.Data;

@Data
public class GroupUpdateRequest {

    private String ownerEmail;
    private String name;
    private String description;
    private String headerImg;
    private String profilePic;

}
