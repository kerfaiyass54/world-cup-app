package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.Cup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CupRepository extends JpaRepository<Cup, Long> {
}
