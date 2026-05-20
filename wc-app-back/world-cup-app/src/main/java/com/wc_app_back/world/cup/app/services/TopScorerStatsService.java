package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.entities.TopScorerGoals;
import com.wc_app_back.world.cup.app.repositories.TopScorerGoalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopScorerStatsService {

    private final TopScorerGoalsRepository repo;

    public List<TopScorerGoals> getAllTopScorers() {
        return repo.findAll();
    }

    public List<String> getTopScorerTitles() {

        return repo.findAll()
                .stream()
                .map(item ->
                        item.getTopScorerPlayer()
                                + " - "
                                + item.getYear()
                                + " (" + item.getTopScorerGoals() + " goals)"
                )
                .collect(Collectors.toList());
    }
}