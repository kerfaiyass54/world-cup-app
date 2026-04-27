package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.MatchesGrowth;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchesGrowthRepository extends JpaRepository<MatchesGrowth, Long> {
    MatchesGrowth findByYear(Integer year);
}