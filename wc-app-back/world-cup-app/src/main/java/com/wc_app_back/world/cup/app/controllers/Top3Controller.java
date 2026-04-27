package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.dtos.Top3ConsistencyDTO;
import com.wc_app_back.world.cup.app.dtos.Top3StatsDTO;
import com.wc_app_back.world.cup.app.services.Top3Services;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/top3")
@RequiredArgsConstructor
public class Top3Controller {

    private final Top3Services service;


    // =========================
    // 1️⃣ Appearances
    // =========================
    @GetMapping("/stats")
    public ResponseEntity<List<Top3StatsDTO>> getStats() {
        return ResponseEntity.ok(service.getTop3Stats());
    }




    // =========================
    // 3️⃣ Top consistent
    // =========================
    @GetMapping("/consistency/top")
    public ResponseEntity<List<Top3ConsistencyDTO>> getTopConsistency(
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(service.getTopConsistent(limit));
    }



}