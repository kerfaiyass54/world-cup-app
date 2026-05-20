package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.entities.YearlySummary;
import com.wc_app_back.world.cup.app.services.WorldCupYearlySummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yearly-summary")
@RequiredArgsConstructor
public class WorldCupYearlySummaryController {

    private final WorldCupYearlySummaryService service;

    @GetMapping
    public ResponseEntity<List<YearlySummary>> getAllCompetitions() {

        return ResponseEntity.ok(
                service.getAllCompetitions()
        );
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getCompetitionNames() {

        return ResponseEntity.ok(
                service.getCompetitionNames()
        );
    }

    @GetMapping("/{year}")
    public ResponseEntity<YearlySummary> getCompetitionByYear(
            @PathVariable Integer year
    ) {

        return ResponseEntity.ok(
                service.getCompetitionByYear(year)
        );
    }
}