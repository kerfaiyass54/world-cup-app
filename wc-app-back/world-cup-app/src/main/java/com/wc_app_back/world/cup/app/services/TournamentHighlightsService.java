package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.entities.BestTopScorer;
import com.wc_app_back.world.cup.app.entities.HighestAttendance;
import com.wc_app_back.world.cup.app.entities.HighestAvgAttendance;
import com.wc_app_back.world.cup.app.repositories.BestTopScorerRepository;
import com.wc_app_back.world.cup.app.repositories.HighestAttendanceRepository;
import com.wc_app_back.world.cup.app.repositories.HighestAverageAttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentHighlightsService {

    private final HighestAttendanceRepository highestAttendanceRepo;
    private final HighestAverageAttendanceRepository highestAvgAttendanceRepo;
    private final BestTopScorerRepository bestTopScorerRepo;

    public HighestAttendance getHighestAttendance() {

        return highestAttendanceRepo.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public HighestAvgAttendance getHighestAvgAttendance() {

        return highestAvgAttendanceRepo.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public BestTopScorer getBestTopScorer() {

        return bestTopScorerRepo.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }
}