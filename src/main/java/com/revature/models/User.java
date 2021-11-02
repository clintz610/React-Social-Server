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
    private String uid;

    @Column(unique = false, nullable = false)
    private String email;
}
