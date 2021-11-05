package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@EnableAutoConfiguration
@Entity
@Table(name = "Reverb_Profile")

public class Profile {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    @Column(unique = false, nullable = false)
    private String first_name;
    
    @Column(unique = false, nullable = false)
    private String last_name;
    
    @Column(unique = false, nullable = false)
    private String profile_img;
    
    @Column(unique = false, nullable = false)
    private String header_img;
    
    @Column(unique = false, nullable = false)
    @Type(type = "text")
    private String about_me;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
   
    public Profile() {
    	this.first_name = "Reverb";
    	this.last_name = "User";
    	this.profile_img = "https://i.pinimg.com/originals/ca/f3/93/caf393479404b953bc5368a63c32e4e4.png";
    	this.header_img = "https://www.windowslatest.com/wp-content/uploads/2017/10/Windows-XP-min.jpg";
    	this.about_me = "I just joined Reverb!";
    }
}
