package com.userprofiles.wc_user_profiles.repositories;

import com.userprofiles.wc_user_profiles.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findByNationality(String nationality);

    List<Profile> findByLiving(boolean living);

    // If you added userId to Profile
    List<Profile> findByUserId(Long userId);
}