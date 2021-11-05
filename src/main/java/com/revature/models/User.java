package com.revature.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reverb_User")
public class User {
	//ID is coming from firebase, will be unique for each user.
    @Id
    private String uid;

    @Column(unique = true, nullable = false)
    private String email;
}
