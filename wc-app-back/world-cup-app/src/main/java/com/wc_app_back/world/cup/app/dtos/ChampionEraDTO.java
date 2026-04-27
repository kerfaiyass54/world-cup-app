package com.wc_app_back.world.cup.app.dtos;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChampionEraDTO {

    private String era;
    private Integer maxWins;
    private List<String> topCountries;
}