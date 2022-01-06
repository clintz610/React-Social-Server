package com.revature.users.dtos;

import lombok.Data;

@Data
public class ProfileRequest {
    private String id;
    private String first_name;
    private String last_name;
    private String birthday;
    private String hobby;
    private String location;
    private String profile_img;
    private String header_img;
    private String about_me;
}
