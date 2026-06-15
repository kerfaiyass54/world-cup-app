package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.TeamStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamStatisticsRepository
        extends JpaRepository<TeamStatistics, Long> {
}