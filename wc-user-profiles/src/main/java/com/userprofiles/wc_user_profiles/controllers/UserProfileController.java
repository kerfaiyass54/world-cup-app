package com.userprofiles.wc_user_profiles.controllers;


import com.userprofiles.wc_user_profiles.dtos.ProfileDTO;
import com.userprofiles.wc_user_profiles.dtos.UserDTO;
import com.userprofiles.wc_user_profiles.entities.Profile;
import com.userprofiles.wc_user_profiles.entities.User;
import com.userprofiles.wc_user_profiles.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(
            @RequestBody UserDTO userDTO
    ) {

        return ResponseEntity.ok(
                userProfileService.saveUser(userDTO)
        );
    }

    @PostMapping("/users/{userId}/profile")
    public ResponseEntity<Profile> saveProfile(
            @PathVariable Long userId,
            @RequestBody ProfileDTO profileDTO
    ) {

        return ResponseEntity.ok(
                userProfileService.saveProfile(
                        userId,
                        profileDTO
                )
        );
    }

    @PutMapping("/profiles/{profileId}")
    public ResponseEntity<Profile> editProfile(
            @PathVariable Long profileId,
            @RequestBody ProfileDTO profileDTO
    ) {

        return ResponseEntity.ok(
                userProfileService.editProfile(
                        profileId,
                        profileDTO
                )
        );
    }

    @GetMapping("/users/profile-exists")
    public ResponseEntity<Boolean> hasProfile(
            @RequestParam String email
    ) {

        return ResponseEntity.ok(
                userProfileService.hasProfile(email)
        );
    }



    @GetMapping("/profiles/by-email")
    public ResponseEntity<Profile> getProfileByEmail(
            @RequestParam String email
    ) {

        return ResponseEntity.ok(
                userProfileService.getProfileByEmail(email)
        );
    }
}