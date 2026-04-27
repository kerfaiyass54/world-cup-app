package com.wc_app_back.world.cup.app.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdPlaceDTO {

    private String country;
    private Integer thirdplaceCount;
}