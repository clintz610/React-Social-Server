package com.revature.models;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class PostMeta {

    @Id
    @Column(name="post_meta_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//use when we want database to create pk
    private UUID id;

    //add unique = true whenever there is a constraint, if we want an error thrown for non-unique values
    @ManyToOne
    @JoinColumn(name="author_id_fk", referencedColumnName="user_id", unique = true)//every foreign key should have JoinColumn
    private User author;

    @ManyToOne
    @JoinColumn(name="group_id_fk", referencedColumnName = "group_id", unique = true)//referenceColumnName should match ERD syntax
    private Group group;

    @Enumerated(EnumType.STRING)//add when passing labels or values as configuration
    private String contentType;

    @OneToOne
    @JoinColumn(name="post_content_fk", referencedColumnName = "post_id", unique = true)
    private Post postContent;

    //if not pk or fk, then use @Column annotation
    @Column(name="creation_date") //name should be explicit
    private LocalDateTime date;



}
