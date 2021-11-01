package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Reverb_User")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(unique = false, nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @Column(unique = false, nullable = false)
    private Profile profile;


}
