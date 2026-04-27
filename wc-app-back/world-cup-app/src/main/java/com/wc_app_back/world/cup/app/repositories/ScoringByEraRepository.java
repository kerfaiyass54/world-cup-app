package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.ScoringByEra;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoringByEraRepository extends JpaRepository<ScoringByEra, Long> {
    ScoringByEra findByEra(String era);
}