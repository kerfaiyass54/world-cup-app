package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.RunnerupWithoutTitles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunnerupWithoutTitlesRepository extends JpaRepository<RunnerupWithoutTitles, Long> {
    boolean existsByCountry(String country);
}