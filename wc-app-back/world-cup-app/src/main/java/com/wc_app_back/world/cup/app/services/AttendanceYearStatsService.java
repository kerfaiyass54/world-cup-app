package com.wc_app_back.world.cup.app.services;

import com.wc_app_back.world.cup.app.entities.AttendanceByYear;
import com.wc_app_back.world.cup.app.entities.AvgAttendanceByYear;
import com.wc_app_back.world.cup.app.repositories.AttendanceByYearRepository;
import com.wc_app_back.world.cup.app.repositories.AverageAttendanceByYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceYearStatsService {

    private final AttendanceByYearRepository attendanceRepo;
    private final AverageAttendanceByYearRepository avgAttendanceRepo;

    public List<AttendanceByYear> getAttendanceByYear() {
        return attendanceRepo.findAll();
    }

    public List<AvgAttendanceByYear> getAverageAttendanceByYear() {
        return avgAttendanceRepo.findAll();
    }
}