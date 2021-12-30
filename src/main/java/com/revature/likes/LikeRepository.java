package com.revature.likes;

import com.revature.posts.Post;
import com.revature.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> getLikeByPost(Post post);
    List<Like> getByPostAndUser(Post post, User user);
}
