package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.dtos.*;
import com.wc_app_back.world.cup.app.entities.MatchesGrowth;
import com.wc_app_back.world.cup.app.entities.TeamsGrowth;
import com.wc_app_back.world.cup.app.repositories.MatchesGrowthRepository;
import com.wc_app_back.world.cup.app.repositories.TeamsGrowthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StructureService {

    private final TeamsGrowthRepository teamsRepo;
    private final MatchesGrowthRepository matchesRepo;


    // =========================
    // 1️⃣ Teams growth
    // =========================
    public List<TeamsGrowthDTO> getTeamsGrowth() {

        return teamsRepo.findAllByOrderByYearAsc()
                .stream()
                .map(t -> TeamsGrowthDTO.builder()
                        .year(t.getYear())
                        .teams(t.getTeams())
                        .build())
                .toList();
    }


    // =========================
    // 2️⃣ Matches growth
    // =========================
    public List<MatchesGrowthDTO> getMatchesGrowth() {

        return matchesRepo.findAllByOrderByYearAsc()
                .stream()
                .map(m -> MatchesGrowthDTO.builder()
                        .year(m.getYear())
                        .matches(m.getMatches())
                        .build())
                .toList();
    }


    // =========================
    // 3️⃣ Combined (BEST FOR CHARTS)
    // =========================
    public List<StructureGrowthDTO> getCombinedGrowth() {

        List<TeamsGrowth> teams = teamsRepo.findAllByOrderByYearAsc();
        List<MatchesGrowth> matches = matchesRepo.findAllByOrderByYearAsc();

        Map<Integer, Integer> matchesMap = matches.stream()
                .collect(Collectors.toMap(
                        MatchesGrowth::getYear,
                        MatchesGrowth::getMatches
                ));

        return teams.stream()
                .map(t -> StructureGrowthDTO.builder()
                        .year(t.getYear())
                        .teams(t.getTeams())
                        .matches(matchesMap.getOrDefault(t.getYear(), 0))
                        .build())
                .toList();
    }
}