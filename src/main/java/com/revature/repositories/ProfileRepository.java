package com.revature.repositories;

import java.util.Optional;

import com.revature.models.Profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public Optional<Profile> findById(int id);
    
}
