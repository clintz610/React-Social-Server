package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group")
@Entity
public class Group {

    @Id
    @Column(name = "group_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id_fk", referencedColumnName = "user_id")
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(columnDefinition = "default = 'https://www.windowslatest.com/wp-content/uploads/2017/10/Windows-XP-min.jpg'")
    private String headerImg;

    @Column(columnDefinition = "default = 'https://i.pinimg.com/originals/ca/f3/93/caf393479404b953bc5368a63c32e4e4.png'")
    private String profilePic;

    @ManyToMany
    @JoinColumns({
            @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id"),
            @JoinColumn(name = "group_id_fk", referencedColumnName = "group_id")
    })
    private List<User> users;

}
