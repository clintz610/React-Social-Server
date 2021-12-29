package com.revature.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ToString
@RequiredArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Table(name = "groups")
@Entity
public class Group {

    @Id
    @Column(name = "group_id")
    @GeneratedValue()
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id_fk", referencedColumnName = "user_id")
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column()
    private String headerImg;

    @Column()
    private String profilePic;

    @ManyToMany()
    @JoinTable
    @ToString.Exclude
    private List<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) && Objects.equals(owner, group.owner) && Objects.equals(name, group.name) && Objects.equals(description, group.description) && Objects.equals(headerImg, group.headerImg) && Objects.equals(profilePic, group.profilePic) && Objects.equals(users, group.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, description, headerImg, profilePic, users);
    }


}
