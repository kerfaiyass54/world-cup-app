package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.TeamsGrowth;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamsGrowthRepository extends JpaRepository<TeamsGrowth, Long> {
    TeamsGrowth findByYear(Integer year);
}