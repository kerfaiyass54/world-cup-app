package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.entities.AttendanceByHost;
import com.wc_app_back.world.cup.app.entities.AvgAttendanceByHost;
import com.wc_app_back.world.cup.app.repositories.AttendanceByHostRepository;
import com.wc_app_back.world.cup.app.repositories.AverageAttendanceByHostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceHostStatsService {

    private final AttendanceByHostRepository attendanceRepo;
    private final AverageAttendanceByHostRepository avgAttendanceRepo;

    public List<AttendanceByHost> getAttendanceByHost() {
        return attendanceRepo.findAll();
    }

    public List<AvgAttendanceByHost> getAverageAttendanceByHost() {
        return avgAttendanceRepo.findAll();
    }
}