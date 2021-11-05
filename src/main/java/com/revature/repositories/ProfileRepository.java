package com.revature.repositories;

import com.revature.models.Profile;
import com.revature.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
	
	Optional<Profile> getProfileByUser(User user);
}
