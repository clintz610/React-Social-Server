package com.revature.repositories;

import com.revature.models.Like;
import com.revature.models.Post;
import io.opencensus.stats.Aggregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> getLikeByPost(Post post);
}
