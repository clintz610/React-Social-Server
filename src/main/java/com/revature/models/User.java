package com.revature.models;

import lombok.*;
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
    private Profile profile = new Profile();

    public String getFirstName() {
        return this.profile.getFirst_name();
    }

    public void setFirstName(String s) {
        this.profile.setFirst_name(s);
    }
}
