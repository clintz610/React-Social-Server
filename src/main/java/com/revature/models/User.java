package com.revature.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reverb_User")
public class User {
	//ID is coming from firebase, will be unique for each user.

    //Following: join table connection between users
    @Id
    @ManyToMany
    @JoinColumn(name="user_id", referencedColumnName = "user_id", unique = true)
    private String uid;

    @Column(unique = true, nullable = false)
    private String email;


    @ManyToMany
    @JoinColumns({@JoinColumn(name="followee_id"),
                  @JoinColumn(name="follower_id")})
    private List<User> following;

//    @ManyToMany
//    @JoinColumns({})
//    private List<User> follower;

}
