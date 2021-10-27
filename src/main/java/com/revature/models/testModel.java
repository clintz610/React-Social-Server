package com.revature.models;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class testModel {
    @Getter  @Setter
    private String username;
    @Getter  @Setter
    private String fname;
    @Getter  @Setter
    private String lname;
    @Getter  @Setter
    private String password;
}
