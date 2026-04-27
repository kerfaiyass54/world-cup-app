package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.Top3Stats;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Top3StatsRepository extends JpaRepository<Top3Stats, Long> {
    Top3Stats findByCountry(String country);
}