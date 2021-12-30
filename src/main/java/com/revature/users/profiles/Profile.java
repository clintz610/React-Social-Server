package com.revature.users.profiles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.revature.users.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@EnableAutoConfiguration
@Entity
@Table(name = "user_profile")
public class Profile {

    @Id
    @Column(name="id", unique = true)
    @GeneratedValue
    private UUID id;
	
    @Column(name="first_name", unique = false, nullable = false)
    private String first_name;
    
    @Column(name="last_name",unique = false, nullable = false)
    private String last_name;
    
    @Column(name="profile_img",unique = false, nullable = false)
    private String profile_img;
    
    @Column(name="header_img",unique = false, nullable = false)
    private String header_img;

    @Column(name="birthday",unique = false, nullable = false)
    private String birthday;

    @Column(name="hobby",unique = false, nullable = false)
    private String hobby;

    @Column(name="location", unique = false, nullable = false)
    private String location;
    
    @Column(name="about_me", unique = false, nullable = false)
    private String about_me;

    @OneToOne
    @JoinColumn(name="user_id_fk", unique = true, referencedColumnName = "user_id")
    private User user;


    public Profile() {
    	this.first_name = "Reverb";
    	this.last_name = "User";
    	this.profile_img = "https://i.pinimg.com/originals/ca/f3/93/caf393479404b953bc5368a63c32e4e4.png";
    	this.header_img = "https://www.windowslatest.com/wp-content/uploads/2017/10/Windows-XP-min.jpg";
        this.birthday = "A Mystery...";
        this.hobby = "Programming, surely";
        this.location = "Planet Earth";
    	this.about_me = "I just joined Reverb!";
    }
}
