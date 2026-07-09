package com.exihibition.wc_exihibit.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecentMatchDto {

    private String homeTeamId;

    private String awayTeamId;

    private int homeGoals;

    private int awayGoals;
}