package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.entities.BestTopScorer;
import com.wc_app_back.world.cup.app.entities.HighestAttendance;
import com.wc_app_back.world.cup.app.entities.HighestAvgAttendance;
import com.wc_app_back.world.cup.app.services.TournamentHighlightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/highlights")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TournamentHighlightsController {

    private final TournamentHighlightsService service;

    @GetMapping("/highest-attendance")
    public ResponseEntity<HighestAttendance> getHighestAttendance() {

        return ResponseEntity.ok(
                service.getHighestAttendance()
        );
    }

    @GetMapping("/highest-avg-attendance")
    public ResponseEntity<HighestAvgAttendance> getHighestAverageAttendance() {

        return ResponseEntity.ok(
                service.getHighestAvgAttendance()
        );
    }

    @GetMapping("/best-top-scorer")
    public ResponseEntity<BestTopScorer> getBestTopScorer() {

        return ResponseEntity.ok(
                service.getBestTopScorer()
        );
    }
}