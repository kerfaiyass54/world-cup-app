package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.StructureByEra;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StructureByEraRepository extends JpaRepository<StructureByEra, Long> {
    StructureByEra findByEra(String era);
}