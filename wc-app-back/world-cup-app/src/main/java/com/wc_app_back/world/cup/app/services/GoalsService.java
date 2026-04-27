package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.dtos.*;
import com.wc_app_back.world.cup.app.entities.*;
import com.wc_app_back.world.cup.app.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoalsService {

    private final ScoringCorrStatsRepository corrRepo;
    private final ScoringByEraRepository eraRepo;


    // =========================
    // 1️⃣ Correlation (teams vs scoring)
    // =========================
    public ScoringCorrDTO getTeamsScoringCorrelation() {

        ScoringCorrStats stat = corrRepo.findByMetric("teams_scoring_corr");

        return ScoringCorrDTO.builder()
                .metric(stat.getMetric())
                .value(stat.getValue())
                .build();
    }


    // =========================
    // 2️⃣ Scoring by era
    // =========================
    public List<ScoringByEraDTO> getScoringByEra() {

        return eraRepo.findAllByOrderByEraAsc()
                .stream()
                .map(e -> ScoringByEraDTO.builder()
                        .era(e.getEra())
                        .avgGoalsPerGame(e.getAvgGoalsPerGame())
                        .build())
                .toList();
    }





}