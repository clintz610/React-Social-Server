package com.revature.users.dtos;

import com.revature.users.profiles.Profile;
import lombok.Data;

@Data
public class ProfileResponse {
    private String id;
    private String first_name;
    private String last_name;
    private String birthday;
    private String hobby;
    private String location;
    private String profile_img;
    private String header_img;
    private String about_me;

    public ProfileResponse(Profile raw){
        this.id = raw.getId().toString();
        this.first_name = raw.getFirstName();
        this.last_name = raw.getLastName();
        this.birthday = raw.getBirthday();
        this.hobby = raw.getHobby();
        this.location = raw.getLocation();
        this.profile_img = raw.getProfileImg();
        this.header_img = raw.getHeaderImg();
        this.about_me = raw.getAboutMe();
    }
}
