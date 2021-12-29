package com.revature.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
//@ToString(exclude = {"comments"})
//@EqualsAndHashCode(exclude = {"comments"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//database generates rather than hybernate
    private UUID id;

    @Column(name = "post_text")
    private String postText;

    @Column(name = "content_link")
    private String contentLink;

    public Post(String postText, String contentLink)
    {
        this.postText = postText;
        this.contentLink = contentLink;
    }

}
