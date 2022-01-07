package com.revature.posts.postmeta;
import com.revature.groups.Group;
import com.revature.common.util.ContentType;
import com.revature.users.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "post_meta")
@Getter
@Setter
public class PostMeta {

    @Id
    @Column(name="post_meta_id", unique = true)
    @GeneratedValue
    @Type(type="uuid-char")
    private UUID id;

    //add unique = true whenever there is a constraint, if we want an error thrown for non-unique values
    @ManyToOne
    @JoinColumn(name="author_id_fk", referencedColumnName="user_id")//every foreign key should have JoinColumn
    private User author;

    // The group that a post is placed in.
    @ManyToOne
    @JoinColumn(name="group_id_fk", referencedColumnName = "group_id")
    private Group group;

    // Options for content
    @Enumerated(EnumType.STRING)
    private ContentType contentType;


    //if not pk or fk, then use @Column annotation
    @Column(name="creation_date") //name should be explicit
    private LocalDateTime date;





}
