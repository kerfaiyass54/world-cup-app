package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.WorldCupSummaryStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldCupSummaryRepository extends JpaRepository<WorldCupSummaryStats, Long> {
}