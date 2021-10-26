package com.revature.models;

import lombok.*;

import javax.persistence.*;

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
    private Long id;
    private String postText;
    private String contentInfo;

    //Convene with team that deals with username information

    public Post(String postText, String contentInfo)
    {
       this.postText = postText;
       this.contentInfo = contentInfo;
    }
}
