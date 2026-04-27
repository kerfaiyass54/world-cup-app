package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.ThirdplaceStats;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThirdplaceStatsRepository extends JpaRepository<ThirdplaceStats, Long> {
    Optional findByCountry(String country);

    List<ThirdplaceStats> findAllByOrderByThirdplaceCountDesc();

}