package com.wc_app_back.world.cup.app.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamsGrowthDTO {

    private Integer year;
    private Integer teams;
}