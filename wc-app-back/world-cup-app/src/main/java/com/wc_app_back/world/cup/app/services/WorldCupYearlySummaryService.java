package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.entities.YearlySummary;
import com.wc_app_back.world.cup.app.repositories.WorldCupYearlySummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorldCupYearlySummaryService {

    private final WorldCupYearlySummaryRepository repo;

    public List<YearlySummary> getAllCompetitions() {
        return repo.findAll();
    }

    public List<String> getCompetitionNames() {

        return repo.findAll()
                .stream()
                .map(item -> item.getHost() + " " + item.getYear())
                .collect(Collectors.toList());
    }

    public YearlySummary getCompetitionByYear(Integer year) {

        return repo.findAll()
                .stream()
                .filter(item -> item.getYear().equals(year))
                .findFirst()
                .orElse(null);
    }
}