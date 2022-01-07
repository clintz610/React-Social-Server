package com.revature.users.profiles;

import java.util.Optional;
import java.util.UUID;

import com.revature.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
	Optional<Profile> getProfileByUser(User user);

    @Modifying
    @Query(value = "update user_profile up set up.profile_img = ':url' where up.id = ':profile_id'", nativeQuery = true)
    void updatePicUrl(UUID id, String savedURL);

}
