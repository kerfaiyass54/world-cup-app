package com.wc_app_back.world.cup.app.dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChampionDTO {

    private String country;
    private Integer wins;
}