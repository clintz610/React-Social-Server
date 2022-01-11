package com.revature.posts;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import com.revature.comments.Comment;
import com.revature.posts.postmeta.PostMeta;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

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
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "post_text", columnDefinition = "varchar(1000)")
    private String postText;

    @Column(name = "content_link")
    private String contentLink;

    // Link to the post's meta data
    @OneToOne
    @JoinColumn(name="post_content_fk", referencedColumnName = "post_meta_id", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostMeta postMeta;

    // Public list of comments
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;


    public Post(String postText, String contentLink)
    {
        this.postText = postText;
        this.contentLink = contentLink;
    }


}
