package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.TopScorerGoals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopScorerGoalsRepository
        extends JpaRepository<TopScorerGoals, Long> {
}