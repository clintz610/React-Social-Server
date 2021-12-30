package com.revature.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.posts.Post;
import com.revature.users.User;
import lombok.*;

import javax.persistence.*;


@ToString(exclude = {"post"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentText;

    private String date;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="author", referencedColumnName="user_id")
    private User author;


    public Comment(String commentText)
    {
        this.commentText = commentText;
    }
}
