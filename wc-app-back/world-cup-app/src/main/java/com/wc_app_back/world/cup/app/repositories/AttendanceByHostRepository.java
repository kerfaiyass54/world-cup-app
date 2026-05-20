package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.AttendanceByHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceByHostRepository
        extends JpaRepository<AttendanceByHost, Long> {

    AttendanceByHost findByHost(String host);
}