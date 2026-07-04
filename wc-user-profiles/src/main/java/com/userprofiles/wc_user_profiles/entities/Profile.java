package com.userprofiles.wc_user_profiles.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "profiles",
        indexes = {
                @Index(name = "idx_profile_user_id", columnList = "user_id"),
                @Index(name = "idx_profile_nationality", columnList = "nationality")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 100)
    private String nationality;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean living;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}