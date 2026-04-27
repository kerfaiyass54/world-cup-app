package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.dtos.*;
import com.wc_app_back.world.cup.app.services.StructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/structure")
@RequiredArgsConstructor
public class StructureController {

    private final StructureService service;


    @GetMapping("/teams")
    public ResponseEntity<List<TeamsGrowthDTO>> teamsGrowth() {
        return ResponseEntity.ok(service.getTeamsGrowth());
    }


    @GetMapping("/matches")
    public ResponseEntity<List<MatchesGrowthDTO>> matchesGrowth() {
        return ResponseEntity.ok(service.getMatchesGrowth());
    }


    // 🔥 BEST ENDPOINT
    @GetMapping("/combined")
    public ResponseEntity<List<StructureGrowthDTO>> combinedGrowth() {
        return ResponseEntity.ok(service.getCombinedGrowth());
    }
}