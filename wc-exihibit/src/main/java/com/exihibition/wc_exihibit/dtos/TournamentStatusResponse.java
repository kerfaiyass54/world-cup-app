package com.exihibition.wc_exihibit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentStatusResponse {

    private String tournamentId;

    private boolean started;

    private String stage;

    private boolean groupsFinished;

    private Integer qualifiedTeams;

    private String championTeamId;
}