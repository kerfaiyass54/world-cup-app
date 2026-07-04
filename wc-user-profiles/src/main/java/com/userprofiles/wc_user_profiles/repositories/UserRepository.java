package com.userprofiles.wc_user_profiles.repositories;

import com.userprofiles.wc_user_profiles.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}