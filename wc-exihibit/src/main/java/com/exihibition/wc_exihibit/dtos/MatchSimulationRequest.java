package com.exihibition.wc_exihibit.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchSimulationRequest {

    private String matchId;

    private String tournamentId;

    private String homeTeamId;
    private String awayTeamId;

    private String homeTeamName;
    private String awayTeamName;

    private Integer homeAbility;
    private Integer awayAbility;

    private String homeMorale;
    private String awayMorale;

    private String homeFormation;
    private String awayFormation;

    private String homeTactic;
    private String awayTactic;

    private boolean isKnockout;
}