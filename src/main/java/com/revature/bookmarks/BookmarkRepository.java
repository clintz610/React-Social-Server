package com.revature.bookmarks;

import com.revature.posts.Post;
import com.revature.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> getBookmarkByPost(Post post);
    List<Bookmark> getByPostAndUser(Post post, User user);
    List<Bookmark> getByUser(User user);
}
