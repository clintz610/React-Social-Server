package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @SequenceGenerator(
        name = "post_sequence",
        sequenceName = "post_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "post_sequence"
    )
    @Column(name = "post_id")
    private Long id;
    private String postText;
    private String contentInfo;

    //Convene with team that deals with username information

    @JsonIgnore
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<Comment> comment;


    public Post(String postText, String contentInfo)
    {
       this.postText = postText;
       this.contentInfo = contentInfo;
    }
}
