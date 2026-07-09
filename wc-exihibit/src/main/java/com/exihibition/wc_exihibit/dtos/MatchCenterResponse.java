package com.exihibition.wc_exihibit.dtos;

import com.exihibition.wc_exihibit.enums.KnockoutType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchCenterResponse {

    private String matchId;

    private String teamHomeId;

    private String teamAwayId;

    private Integer goalsHome;

    private Integer goalsAway;

    private boolean played;

    private String groupName;

    private KnockoutType knockoutType;

    private String winnerTeamId;
}