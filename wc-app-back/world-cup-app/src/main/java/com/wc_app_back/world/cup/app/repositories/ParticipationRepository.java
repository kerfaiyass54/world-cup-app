package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
}
