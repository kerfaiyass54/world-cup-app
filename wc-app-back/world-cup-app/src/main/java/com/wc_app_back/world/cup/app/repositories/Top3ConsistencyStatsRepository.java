package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.Top3ConsistencyStats;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Top3ConsistencyStatsRepository extends JpaRepository<Top3ConsistencyStats, Long> {
    Top3ConsistencyStats findByCountry(String country);
}