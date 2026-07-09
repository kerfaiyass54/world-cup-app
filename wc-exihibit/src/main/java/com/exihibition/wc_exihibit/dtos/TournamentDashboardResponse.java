package com.exihibition.wc_exihibit.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TournamentDashboardResponse {

    private int matchesPlayed;

    private int goalsScored;

    private int qualifiedTeams;

    private String knockoutStage;

    private String bestAttack;

    private String bestDefense;

    private List<RecentMatchDto> recentResults;
}