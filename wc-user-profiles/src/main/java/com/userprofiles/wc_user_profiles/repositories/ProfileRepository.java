package com.userprofiles.wc_user_profiles.repositories;

import com.userprofiles.wc_user_profiles.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository
        extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);
}