package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.entities.TopScorerGoals;
import com.wc_app_back.world.cup.app.services.TopScorerStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/top-scorers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TopScorerStatsController {

    private final TopScorerStatsService service;

    @GetMapping
    public ResponseEntity<List<TopScorerGoals>> getAllTopScorers() {

        return ResponseEntity.ok(
                service.getAllTopScorers()
        );
    }

    @GetMapping("/titles")
    public ResponseEntity<List<String>> getTopScorerTitles() {

        return ResponseEntity.ok(
                service.getTopScorerTitles()
        );
    }
}