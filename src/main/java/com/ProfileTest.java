package com;

import com.revature.models.Profile;

public class ProfileTest {
    public static void main(String[] args) {
        Profile john = new Profile(1, "John", "Lee", "none", "none", "This is just testing");
        String e = john.getFirst_name();
        String l = john.getLast_name();
        String img = john.getProfile_img();
        String himg = john.getHeader_img();
        String n = john.getAbout_me();
        System.out.println(e + " " + l + " " + img + " " + himg + " " + n);

        Profile susan = Profile.builder()
            .id(2)
            .first_name("Susan")
            .last_name("Kwan")
            .profile_img("none")
            .header_img("none")
            .about_me("I don't have anything to say")
            .build();
        System.out.println(susan);
    }
    
}
