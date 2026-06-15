package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.entities.TeamStatistics;
import com.wc_app_back.world.cup.app.services.TeamStatisticsService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class TeamStatisticsController {

    private final TeamStatisticsService service;

    public TeamStatisticsController(
            TeamStatisticsService service) {

        this.service = service;
    }

    @GetMapping
    public List<TeamStatistics> getAll() {

        return service.getAll();
    }
}