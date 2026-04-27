package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.RunnerupStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunnerupStatsRepository extends JpaRepository<RunnerupStats, Long> {

    RunnerupStats findByCountry(String country);

    List<RunnerupStats> findAllByOrderByRunnerupCountDesc();

}