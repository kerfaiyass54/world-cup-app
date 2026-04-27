package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.ScoringCorrStats;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoringCorrStatsRepository extends JpaRepository<ScoringCorrStats, Long> {
    ScoringCorrStats findByMetric(String metric);
}