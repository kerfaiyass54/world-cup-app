package com.wc_app_back.world.cup.app.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StructureGrowthDTO {

    private Integer year;
    private Integer teams;
    private Integer matches;
}