package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.entities.AttendanceByHost;
import com.wc_app_back.world.cup.app.entities.AvgAttendanceByHost;
import com.wc_app_back.world.cup.app.services.AttendanceHostStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance/host")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttendanceHostStatsController {

    private final AttendanceHostStatsService service;

    @GetMapping
    public ResponseEntity<List<AttendanceByHost>> getAttendanceByHost() {

        return ResponseEntity.ok(
                service.getAttendanceByHost()
        );
    }

    @GetMapping("/avg")
    public ResponseEntity<List<AvgAttendanceByHost>> getAverageAttendanceByHost() {

        return ResponseEntity.ok(
                service.getAverageAttendanceByHost()
        );
    }
}