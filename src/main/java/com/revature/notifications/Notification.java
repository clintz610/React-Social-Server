package com.revature.notifications;

        import com.revature.groups.Group;
        import com.revature.notifications.notificationType.NotificationType;
        import com.revature.search.Searchable;
        import com.revature.users.User;
        import com.revature.users.usersettings.UserSettings;
        import lombok.*;
        import org.hibernate.annotations.Type;

        import javax.persistence.*;
        import java.time.LocalDateTime;
        import java.util.List;
        import java.util.Objects;
        import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @Column(name = "notification_id", unique = true)
    @GeneratedValue()
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id_fk", referencedColumnName = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private NotificationType type_id;

    @Column(name = "creation_date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "other_user_id_fk", referencedColumnName = "user_id")//every foreign key should have JoinColumn
    private User otherUser;

}