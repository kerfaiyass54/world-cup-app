package com.userprofiles.wc_user_profiles.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Long id;

    private String name;

    private String nationality;

    private String description;

    private boolean living;
}