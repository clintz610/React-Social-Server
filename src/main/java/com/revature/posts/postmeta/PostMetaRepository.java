package com.revature.posts.postmeta;

import com.revature.groups.Group;
import com.revature.posts.postmeta.PostMeta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostMetaRepository extends CrudRepository<PostMeta, UUID> {

    public List<PostMeta> findPostMetaByGroup(Group group);
}
