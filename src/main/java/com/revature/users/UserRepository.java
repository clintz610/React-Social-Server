package com.revature.users;
import com.revature.search.Searchable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<Searchable> findByEmailContains(String email);
    Optional<User> findUserByEmail(String ownerEmail);
}
