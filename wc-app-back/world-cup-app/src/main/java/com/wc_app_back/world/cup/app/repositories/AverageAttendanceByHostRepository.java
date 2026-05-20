package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.AvgAttendanceByHost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AverageAttendanceByHostRepository
        extends JpaRepository<AvgAttendanceByHost, Long> {

    AvgAttendanceByHost findByHost(String host);
}