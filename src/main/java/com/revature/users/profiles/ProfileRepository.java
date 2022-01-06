package com.revature.users.profiles;

import java.util.Optional;

import com.revature.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
	Optional<Profile> getProfileByUser(User user);
}
