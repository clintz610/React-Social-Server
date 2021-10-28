package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(unique = true, nullable = false)
    private String about_me;
       
}
