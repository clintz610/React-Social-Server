package com.revature.users.profiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.revature.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
	Optional<Profile> getProfileByUser(User user);

    @Modifying
    @Transactional
    @Query(value="update user_profile u set u.profile_img = :url where u.id = :id", nativeQuery=true)
    void updatePicUrl(@Param(value="id") String id, @Param(value="url") String url);

    @Modifying
    @Transactional
    @Query(value="update user_profile u set u.header_img = :url where u.id = :id", nativeQuery=true)
    void updateHeaderUrl(@Param(value="id") String id, @Param(value="url") String url);
}
