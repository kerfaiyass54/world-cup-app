package com.userprofiles.wc_user_profiles.repositories;

import com.userprofiles.wc_user_profiles.entities.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProfileRepository extends MongoRepository<Profile, String> {

    List<Profile> findByNationality(String nationality);

    List<Profile> findByLiving(boolean living);

    // If you added userId to Profile
    List<Profile> findByUserId(String userId);
}