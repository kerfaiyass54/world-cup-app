package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.TeamStatistics;
import com.wc_app_back.world.cup.app.repositories.TeamStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamStatisticsConsumer {

    private final TeamStatisticsRepository repository;

    @KafkaListener(topics = "team_statistics")
    public void consume(Map<String, Object> payload) {

        TeamStatistics stats = new TeamStatistics();

        stats.setTeam((String) payload.get("team"));

        stats.setMatchesPlayed(
                ((Number) payload.get("matches_played")).intValue());

        stats.setWins(
                ((Number) payload.get("wins")).intValue());

        stats.setDraws(
                ((Number) payload.get("draws")).intValue());

        stats.setLosses(
                ((Number) payload.get("losses")).intValue());

        stats.setGoalsScored(
                ((Number) payload.get("goals_scored")).intValue());

        stats.setGoalsConceded(
                ((Number) payload.get("goals_conceded")).intValue());

        stats.setGoalDifference(
                ((Number) payload.get("goal_difference")).intValue());

        stats.setAppearances(
                ((Number) payload.get("appearances")).intValue());

        stats.setWinPercentage(
                ((Number) payload.get("win_percentage")).doubleValue());

        stats.setAvgAttendance(
                ((Number) payload.get("avg_attendance")).doubleValue());

        repository.save(stats);

        System.out.println(
                "Saved statistics for " +
                        stats.getTeam()
        );
    }
}