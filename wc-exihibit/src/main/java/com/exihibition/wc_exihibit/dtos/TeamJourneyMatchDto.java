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
public class TeamJourneyMatchDto {

    private String matchId;

    private String opponentId;

    private Integer goalsFor;

    private Integer goalsAgainst;

    private boolean won;

    private boolean draw;

    private boolean lost;

    private String groupName;

    private KnockoutType knockoutType;
}