package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.dtos.*;
import com.wc_app_back.world.cup.app.entities.*;
import com.wc_app_back.world.cup.app.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RunUpThreeService {

    private final RunnerupStatsRepository runnerupRepo;
    private final RunnerupWithoutTitlesRepository runnerupNoTitleRepo;
    private final ThirdplaceStatsRepository thirdplaceRepo;


    // =========================
    // 1️⃣ Runner-up ranking
    // =========================
    public List<RunnerUpDTO> getRunnerUps() {

        return runnerupRepo.findAllByOrderByRunnerupCountDesc()
                .stream()
                .map(r -> RunnerUpDTO.builder()
                        .country(r.getCountry())
                        .runnerupCount(r.getRunnerupCount())
                        .build())
                .toList();
    }


    // =========================
    // 2️⃣ Runner-ups without titles
    // =========================
    public List<RunnerUpWithoutTitlesDTO> getRunnerUpsWithoutTitles() {

        return runnerupNoTitleRepo.findAll()
                .stream()
                .map(r -> new RunnerUpWithoutTitlesDTO(r.getCountry()))
                .toList();
    }


    // =========================
    // 3️⃣ Third-place ranking
    // =========================
    public List<ThirdPlaceDTO> getThirdPlaces() {

        return thirdplaceRepo.findAllByOrderByThirdplaceCountDesc()
                .stream()
                .map(t -> ThirdPlaceDTO.builder()
                        .country(t.getCountry())
                        .thirdplaceCount(t.getThirdplaceCount())
                        .build())
                .toList();
    }




}