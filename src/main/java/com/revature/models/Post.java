package com.revature.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = {"comments"})
@EqualsAndHashCode(exclude = {"comments"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "text")
    private String title;

    @Type(type = "text")
    private String postText;

    @Type(type = "text")
    private String imageURL;

    @Type(type = "text")
    private String date;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="author", referencedColumnName="uid")
    private User author;
    //Convene with Team one to add their annotation here
    //will use the first name and last name for a poster or a commenter
    
    @ManyToOne
    @JoinColumn(name="profile", referencedColumnName="")
    private Profile profile;


    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Post(String postText, String imageURL)
    {
       this.postText = postText;
       this.imageURL = imageURL;
    }
}
