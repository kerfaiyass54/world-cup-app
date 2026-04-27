package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.dtos.Top3ConsistencyDTO;
import com.wc_app_back.world.cup.app.dtos.Top3StatsDTO;
import com.wc_app_back.world.cup.app.entities.Top3ConsistencyStats;
import com.wc_app_back.world.cup.app.entities.Top3Stats;
import com.wc_app_back.world.cup.app.repositories.Top3ConsistencyStatsRepository;
import com.wc_app_back.world.cup.app.repositories.Top3StatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class Top3Services {

    private final Top3StatsRepository statsRepo;
    private final Top3ConsistencyStatsRepository consistencyRepo;


    // =========================
    // 1️⃣ All Top3 appearances
    // =========================
    public List<Top3StatsDTO> getTop3Stats() {

        return statsRepo.findAllByOrderByAppearancesDesc()
                .stream()
                .map(t -> Top3StatsDTO.builder()
                        .country(t.getCountry())
                        .appearances(t.getAppearances())
                        .build())
                .toList();
    }





    // =========================
    // 3️⃣ Most consistent countries (TOP N)
    // =========================
    public List<Top3ConsistencyDTO> getTopConsistent(int limit) {

        return consistencyRepo.findAllByOrderByConsistencyScoreDesc()
                .stream()
                .limit(limit)
                .map(c -> Top3ConsistencyDTO.builder()
                        .country(c.getCountry())
                        .consistencyScore(c.getConsistencyScore())
                        .build())
                .toList();
    }



}