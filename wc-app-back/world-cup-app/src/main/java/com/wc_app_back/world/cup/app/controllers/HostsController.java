package com.wc_app_back.world.cup.app.controllers;

import com.wc_app_back.world.cup.app.dtos.HostMetricsDTO;
import com.wc_app_back.world.cup.app.services.HostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hosts")
@RequiredArgsConstructor
public class HostsController {

    private final HostsService service;

    @GetMapping("/metrics")
    public ResponseEntity<HostMetricsDTO> getMetrics() {

        return ResponseEntity.ok(service.getHostMetrics());
    }
}