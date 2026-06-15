package com.userprofiles.wc_user_profiles.repositories;

import com.userprofiles.wc_user_profiles.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}