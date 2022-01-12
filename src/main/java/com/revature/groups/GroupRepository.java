package com.revature.groups;

import com.revature.search.Searchable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<Group, UUID> {
    Optional<Group> findGroupByName(String name);
    List<Searchable> findByNameContains(String name);
}
