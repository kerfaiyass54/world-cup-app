package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.dtos.ChampionDTO;
import com.wc_app_back.world.cup.app.dtos.ChampionEraDTO;
import com.wc_app_back.world.cup.app.entities.ChampionEraStats;
import com.wc_app_back.world.cup.app.entities.ChampionStats;
import com.wc_app_back.world.cup.app.repositories.ChampionEraStatsRepository;
import com.wc_app_back.world.cup.app.repositories.ChampionStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChampionsService {

    private final ChampionStatsRepository championRepo;
    private final ChampionEraStatsRepository eraRepo;


    // =========================
    // 1️⃣ All champions
    // =========================
    public List<ChampionDTO> getAllChampions() {

        return championRepo.findAll()
                .stream()
                .map(c -> ChampionDTO.builder()
                        .country(c.getCountry())
                        .wins(c.getWins())
                        .build())
                .sorted(Comparator.comparing(ChampionDTO::getWins).reversed())
                .toList();
    }




    // =========================
    // 3️⃣ Champions by ERA (ADVANCED)
    // =========================
    public List<ChampionEraDTO> getChampionsByEra() {

        List<ChampionEraStats> data = eraRepo.findAll();

        // group by era
        Map<String, List<ChampionEraStats>> grouped =
                data.stream().collect(Collectors.groupingBy(ChampionEraStats::getEra));

        List<ChampionEraDTO> result = new ArrayList<>();

        for (String era : grouped.keySet()) {

            List<ChampionEraStats> eraData = grouped.get(era);

            int maxWins = eraData.stream()
                    .mapToInt(ChampionEraStats::getWins)
                    .max()
                    .orElse(0);

            List<String> topCountries = eraData.stream()
                    .filter(e -> e.getWins() == maxWins)
                    .map(ChampionEraStats::getCountry)
                    .toList();

            result.add(
                    ChampionEraDTO.builder()
                            .era(era)
                            .maxWins(maxWins)
                            .topCountries(topCountries)
                            .build()
            );
        }

        return result;
    }


    // =========================
    // 4️⃣ Get by country
    // =========================

}