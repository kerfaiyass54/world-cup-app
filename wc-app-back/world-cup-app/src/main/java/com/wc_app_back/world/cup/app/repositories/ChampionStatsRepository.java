package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.ChampionStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionStatsRepository extends JpaRepository<ChampionStats, Long> {

    ChampionStats findByCountry(String country);

    
}