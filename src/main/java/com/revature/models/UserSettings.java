package com.revature.models;

import lombok.*;

import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @OneToOne
    @JoinColumn(name="user_id_fk", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private boolean darkTheme;


}
