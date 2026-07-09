package com.exihibition.wc_exihibit.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResultMessage {

    @JsonProperty("match_id")
    private String matchId;

    @JsonProperty("home_team_id")
    private String homeTeamId;

    @JsonProperty("away_team_id")
    private String awayTeamId;

    @JsonProperty("home_goals")
    private Integer homeGoals;

    @JsonProperty("away_goals")
    private Integer awayGoals;

    @JsonProperty("winner_team_id")
    private String winnerTeamId;

    private Boolean finished;
}