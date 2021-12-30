package com.revature.posts.postmeta;

import com.revature.posts.postmeta.PostMeta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostMetaRepository extends CrudRepository<PostMeta, UUID> {
}
