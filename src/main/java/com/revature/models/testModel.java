package com.revature.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class testModel {
    @Id
    @Getter  @Setter
    private Long userID;

    @Getter  @Setter
    private String username;
    @Getter  @Setter
    private String fname;
    @Getter  @Setter
    private String lname;
    @Getter  @Setter
    private String password;
}
