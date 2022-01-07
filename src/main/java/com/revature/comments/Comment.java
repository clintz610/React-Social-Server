package com.revature.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.posts.Post;
import com.revature.users.User;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@ToString(exclude = {"post"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {

	@Id
    @GeneratedValue()
    @Type(type="uuid-char")
    private UUID id;

    private String commentText;

    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="author", referencedColumnName="user_id")
    private User author;


    public Comment(String commentText)
    {
        this.commentText = commentText;
    }
}
