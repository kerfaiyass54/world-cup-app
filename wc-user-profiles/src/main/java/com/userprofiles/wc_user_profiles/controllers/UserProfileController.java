package com.userprofiles.wc_user_profiles.controllers;


import com.userprofiles.wc_user_profiles.dtos.ProfileDTO;
import com.userprofiles.wc_user_profiles.dtos.UserDTO;
import com.userprofiles.wc_user_profiles.entities.Profile;
import com.userprofiles.wc_user_profiles.entities.User;
import com.userprofiles.wc_user_profiles.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO) {

        User user = userProfileService.saveUser(userDTO);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/users/{userId}/profile")
    public ResponseEntity<Profile> saveProfile(
            @PathVariable Long userId,
            @RequestBody ProfileDTO profileDTO
    ) {

        Profile profile = userProfileService.saveProfile(userId, profileDTO);

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profiles/{profileId}")
    public ResponseEntity<Profile> editProfile(
            @PathVariable Long profileId,
            @RequestBody ProfileDTO profileDTO
    ) {

        Profile profile = userProfileService.editProfile(profileId, profileDTO);

        return ResponseEntity.ok(profile);
    }
}