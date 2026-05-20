package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.YearlySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldCupYearlySummaryRepository
        extends JpaRepository<YearlySummary, Long> {
}