package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.dtos.WorldCupSummaryDTO;
import com.wc_app_back.world.cup.app.services.WorldCupSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class WorldCupSummaryController {

    private final WorldCupSummaryService service;

    @GetMapping
    public ResponseEntity<WorldCupSummaryDTO> getSummary() {
        return ResponseEntity.ok(service.getSummary());
    }
}