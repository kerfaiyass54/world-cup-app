package com.exihibition.wc_exihibit.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    private String id;

    private String teamHomeId;

    private String winnerTeamId;

    private String teamAwayId;

    private Integer goalsHome;

    private Integer goalsAway;

    private String tournamentId;

    private String groupId;

    private String knockoutId;

    private boolean played;
}