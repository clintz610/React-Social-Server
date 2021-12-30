package com.revature.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Data
//@ToString(exclude = {"comments"})
//@EqualsAndHashCode(exclude = {"comments"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue
    private UUID id;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "content_link")
    private String contentLink;

    // Link to the post's meta data
    @OneToOne
    @JoinColumn(name="post_content_fk", referencedColumnName = "post_meta_id", unique = true)
    private PostMeta postMeta;



    public Post(String postText, String contentLink)
    {
        this.postText = postText;
        this.contentLink = contentLink;
    }


}
