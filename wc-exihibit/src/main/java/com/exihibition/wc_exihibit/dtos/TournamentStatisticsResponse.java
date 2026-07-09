package com.exihibition.wc_exihibit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentStatisticsResponse {

    private Integer totalMatches;

    private Integer playedMatches;

    private Integer totalGoals;

    private Double averageGoalsPerMatch;

    private String topScoringTeamId;

    private Integer topScoringTeamGoals;

    private Integer homeGoals;

    private Integer awayGoals;
}