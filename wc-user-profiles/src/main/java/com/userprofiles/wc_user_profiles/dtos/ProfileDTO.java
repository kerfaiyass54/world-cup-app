package com.userprofiles.wc_user_profiles.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private String id;

    private String name;

    private String nationality;

    private String description;

    private boolean living;

    private String userId;
}
