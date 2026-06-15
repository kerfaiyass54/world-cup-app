package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.dtos.TeamStatisticsDTO;
import com.wc_app_back.world.cup.app.entities.TeamStatistics;
import com.wc_app_back.world.cup.app.repositories.TeamStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamStatisticsService {

    private final TeamStatisticsRepository repository;

    public TeamStatisticsService(
            TeamStatisticsRepository repository) {

        this.repository = repository;
    }

    public TeamStatistics save(
            TeamStatisticsDTO dto) {

        TeamStatistics stats =
                new TeamStatistics();

        stats.setTeam(dto.getTeam());
        stats.setMatchesPlayed(
                dto.getMatches_played());
        stats.setWins(dto.getWins());
        stats.setDraws(dto.getDraws());
        stats.setLosses(dto.getLosses());
        stats.setGoalsScored(
                dto.getGoals_scored());
        stats.setGoalsConceded(
                dto.getGoals_conceded());
        stats.setGoalDifference(
                dto.getGoal_difference());
        stats.setWinPercentage(
                dto.getWin_percentage());
        stats.setAvgAttendance(
                dto.getAvg_attendance());
        stats.setAppearances(
                dto.getAppearances());

        return repository.save(stats);
    }

    public List<TeamStatistics> getAll() {

        return repository.findAll();
    }
}