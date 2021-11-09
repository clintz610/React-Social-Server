package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@ToString(exclude = {"post"})
@EqualsAndHashCode(exclude = {"post"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "text")
    private String commentText;

    @Type(type = "text")
    private String date;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="author", referencedColumnName="uid")
    private User author;

    @ManyToOne
    @JoinColumn(name="profile", referencedColumnName="")
    private Profile profile;

    public Comment(String commentText)
    {
        this.commentText = commentText;
    }
}
