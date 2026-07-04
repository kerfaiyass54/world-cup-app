package com.userprofiles.wc_user_profiles.services;


import com.userprofiles.wc_user_profiles.dtos.ProfileDTO;
import com.userprofiles.wc_user_profiles.dtos.UserDTO;
import com.userprofiles.wc_user_profiles.entities.Profile;
import com.userprofiles.wc_user_profiles.entities.User;
import com.userprofiles.wc_user_profiles.repositories.ProfileRepository;
import com.userprofiles.wc_user_profiles.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserProfileService(
            UserRepository userRepository,
            ProfileRepository profileRepository
    ) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    public User saveUser(UserDTO userDTO) {

        return userRepository.findByEmail(userDTO.getEmail())
                .orElseGet(() -> {
                    User user = new User();
                    user.setEmail(userDTO.getEmail());
                    return userRepository.save(user);
                });
    }

    public Profile saveProfile(Long userId, ProfileDTO dto) {

        Profile profile = new Profile();
        profile.setUserId(userId);
        profile.setName(dto.getName());
        profile.setNationality(dto.getNationality());
        profile.setDescription(dto.getDescription());
        profile.setLiving(dto.isLiving());

        return profileRepository.save(profile);
    }

    public Profile editProfile(Long profileId, ProfileDTO dto) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setName(dto.getName());
        profile.setNationality(dto.getNationality());
        profile.setDescription(dto.getDescription());
        profile.setLiving(dto.isLiving());

        return profileRepository.save(profile);
    }
}