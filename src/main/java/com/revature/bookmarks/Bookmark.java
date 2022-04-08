package com.revature.bookmarks;

import com.revature.posts.Post;
import com.revature.users.User;
import lombok.*;

import javax.persistence.*;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bookmarks_table")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="bookmarker_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="bookmarked_post_id", referencedColumnName = "post_id")
    private Post post;
}
