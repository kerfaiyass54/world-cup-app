package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.dtos.*;
import com.wc_app_back.world.cup.app.services.RunUpThreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class RunUpThreeController {

    private final RunUpThreeService service;


    // =========================
    // Runner-ups
    // =========================
    @GetMapping("/runnerups")
    public ResponseEntity<List<RunnerUpDTO>> runnerUps() {
        return ResponseEntity.ok(service.getRunnerUps());
    }


    // =========================
    // Runner-ups without titles
    // =========================
    @GetMapping("/runnerups/no-titles")
    public ResponseEntity<List<RunnerUpWithoutTitlesDTO>> runnerUpsNoTitles() {
        return ResponseEntity.ok(service.getRunnerUpsWithoutTitles());
    }


    // =========================
    // Third place
    // =========================
    @GetMapping("/thirdplace")
    public ResponseEntity<List<ThirdPlaceDTO>> thirdPlaces() {
        return ResponseEntity.ok(service.getThirdPlaces());
    }


}