package com.wc_app_back.world.cup.app.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Top3ConsistencyDTO {

    private String country;
    private Double consistencyScore;
}