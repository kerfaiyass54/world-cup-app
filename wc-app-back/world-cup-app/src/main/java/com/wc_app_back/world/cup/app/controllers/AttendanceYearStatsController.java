package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.entities.AttendanceByYear;
import com.wc_app_back.world.cup.app.entities.AvgAttendanceByYear;
import com.wc_app_back.world.cup.app.services.AttendanceYearStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance/year")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttendanceYearStatsController {

    private final AttendanceYearStatsService service;

    @GetMapping
    public ResponseEntity<List<AttendanceByYear>> getAttendanceByYear() {

        return ResponseEntity.ok(
                service.getAttendanceByYear()
        );
    }

    @GetMapping("/avg")
    public ResponseEntity<List<AvgAttendanceByYear>> getAverageAttendanceByYear() {

        return ResponseEntity.ok(
                service.getAverageAttendanceByYear()
        );
    }
}