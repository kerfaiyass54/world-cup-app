package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.Nation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<Nation,Long> {
}
