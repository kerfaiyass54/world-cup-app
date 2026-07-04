package com.userprofiles.wc_user_profiles.services;

import com.userprofiles.wc_user_profiles.dtos.ProfileDTO;
import com.userprofiles.wc_user_profiles.dtos.UserDTO;
import com.userprofiles.wc_user_profiles.entities.Profile;
import com.userprofiles.wc_user_profiles.entities.User;
import com.userprofiles.wc_user_profiles.repositories.ProfileRepository;
import com.userprofiles.wc_user_profiles.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    public User saveUser(UserDTO userDTO) {

        return userRepository
                .findByEmail(userDTO.getEmail())
                .orElseGet(() ->
                        userRepository.save(
                                User.builder()
                                        .email(userDTO.getEmail())
                                        .build()
                        )
                );
    }

    public Profile saveProfile(
            String email,
            ProfileDTO profileDTO
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        if (user.getProfile() != null) {

            throw new RuntimeException(
                    "Profile already exists"
            );
        }

        Profile profile =
                Profile.builder()
                        .name(profileDTO.getName())
                        .nationality(profileDTO.getNationality())
                        .description(profileDTO.getDescription())
                        .living(profileDTO.isLiving())
                        .user(user)
                        .build();

        Profile savedProfile =
                profileRepository.save(profile);

        user.setProfile(savedProfile);

        userRepository.save(user);

        return savedProfile;
    }

    public Profile editProfile(
            Long profileId,
            ProfileDTO profileDTO
    ) {

        Profile profile =
                profileRepository
                        .findById(profileId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Profile not found"
                                ));

        profile.setName(profileDTO.getName());
        profile.setNationality(profileDTO.getNationality());
        profile.setDescription(profileDTO.getDescription());
        profile.setLiving(profileDTO.isLiving());

        return profileRepository.save(profile);
    }

    public boolean hasProfile(
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        return user.getProfile() != null;
    }

    public Profile getProfileByEmail(
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        if (user.getProfile() == null) {

            throw new RuntimeException(
                    "Profile not found"
            );
        }

        return user.getProfile();
    }
}