package com.revature.notifications.notificationType;

import com.revature.exceptions.NotificationNotFoundExeption;
import com.revature.notifications.Notification;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "notification_type")
public class NotificationType {

    @Id
    @Column(name = "type_id")
    private String id;

    @Column(name = "type", unique = true, nullable = false)
    private String typeName;

    @OneToMany(
            mappedBy = "type_id",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Notification> notification;

    public NotificationType() {
        super();
    }

    public NotificationType(String typeName){
        switch (typeName) {
            case "Like":
                this.id = "1";
                break;
            case "Follow":
                this.id = "2";
                break;
            default:
                throw new NotificationNotFoundExeption();
        }
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationType that = (NotificationType) o;
        return Objects.equals(id, that.id) && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName);
    }

    @Override
    public String toString() {
        return "NotificationType{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                 '}';
    }
}
