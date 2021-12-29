package com.revature.models;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	//ID is coming from firebase, will be unique for each user.

    //Following: join table connection between users
    @Id
    @Column(name="user_id", unique = true)
    @JoinColumn()
    private String id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserSettings userSettings;

    @Column(unique = true, nullable = false)
    private String email;


    @ManyToMany
    @JoinColumns({@JoinColumn(name="followee_id"),
                  @JoinColumn(name="follower_id")})
    private List<User> following;

    @ManyToMany(mappedBy = "users")
    private List<Group> groups;

//    @ManyToMany
//    @JoinColumns({})
//    private List<User> follower;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(following, user.following) && Objects.equals(groups, user.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, following, groups);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
