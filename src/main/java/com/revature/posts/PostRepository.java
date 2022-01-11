package com.revature.posts;

import com.revature.groups.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("from Post p join PostMeta pm on p.postMeta = pm.id where pm.group = :group")
    public List<Post> findPostsByGroupId(Group group);

}
