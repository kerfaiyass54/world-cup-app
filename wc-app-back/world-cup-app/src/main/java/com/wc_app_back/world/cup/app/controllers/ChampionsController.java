package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.dtos.ChampionDTO;
import com.wc_app_back.world.cup.app.dtos.ChampionEraDTO;
import com.wc_app_back.world.cup.app.services.ChampionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/champions")
@RequiredArgsConstructor
public class ChampionsController {

    private final ChampionsService service;


    // =========================
    // 1️⃣ Get all champions
    // =========================
    @GetMapping
    public ResponseEntity<List<ChampionDTO>> getAllChampions() {

        List<ChampionDTO> data = service.getAllChampions();

        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(data);
    }


    // =========================
    // 2️⃣ Get champions by era (advanced insight)
    // =========================
    @GetMapping("/era")
    public ResponseEntity<List<ChampionEraDTO>> getChampionsByEra() {

        List<ChampionEraDTO> data = service.getChampionsByEra();

        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(data);
    }




    // =========================
    // 4️⃣ Get top N champions (useful for charts)
    // =========================
    @GetMapping("/top")
    public ResponseEntity<List<ChampionDTO>> getTopChampions(
            @RequestParam(defaultValue = "5") int limit
    ) {

        List<ChampionDTO> data = service.getAllChampions()
                .stream()
                .limit(limit)
                .toList();

        return ResponseEntity.ok(data);
    }
}