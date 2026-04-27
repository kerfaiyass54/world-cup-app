package com.wc_app_back.world.cup.app.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorldCupSummaryDTO {

    private Integer totalEditions;
    private Integer totalChampions;
    private Integer totalHosts;

    private Double highestAvgGoals;
    private Integer highestAvgGoalsYear;
}