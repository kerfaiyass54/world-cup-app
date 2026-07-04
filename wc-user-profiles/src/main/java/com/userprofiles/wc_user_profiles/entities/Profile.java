package com.userprofiles.wc_user_profiles.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nationality;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean living;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    @JsonBackReference
    private User user;
}