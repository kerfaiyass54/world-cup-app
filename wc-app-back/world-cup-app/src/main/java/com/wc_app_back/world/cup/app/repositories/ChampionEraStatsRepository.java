package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.ChampionEraStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionEraStatsRepository extends JpaRepository<ChampionEraStats, Long> {

    ChampionEraStats findByEraAndCountry(String era, String country);
}