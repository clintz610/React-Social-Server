package com.revature.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @OneToOne
    @JoinColumn(name="user_id_fk", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name="dark_mode",nullable = false)
    private boolean darkMode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserSettings that = (UserSettings) o;
        return user != null && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
