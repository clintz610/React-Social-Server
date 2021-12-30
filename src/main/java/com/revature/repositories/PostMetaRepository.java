package com.revature.repositories;

import com.revature.models.PostMeta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostMetaRepository extends CrudRepository<PostMeta, UUID> {
}
