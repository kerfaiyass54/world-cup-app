package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.dtos.*;
import com.wc_app_back.world.cup.app.services.GoalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalsController {

    private final GoalsService service;


    // =========================
    // Correlation
    // =========================
    @GetMapping("/correlation")
    public ResponseEntity<ScoringCorrDTO> correlation() {
        return ResponseEntity.ok(service.getTeamsScoringCorrelation());
    }


    // =========================
    // Scoring by era (graph)
    // =========================
    @GetMapping("/era")
    public ResponseEntity<List<ScoringByEraDTO>> scoringByEra() {
        return ResponseEntity.ok(service.getScoringByEra());
    }


}