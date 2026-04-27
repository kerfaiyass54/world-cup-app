package com.wc_app_back.world.cup.app.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostMetricsDTO {

    private Integer hostTop3Count;
    private Integer hostFinalCount;
    private Integer hostChampionCount;

    private Integer hostNotTop3Count;

    private Double hostTop3Ratio;
}