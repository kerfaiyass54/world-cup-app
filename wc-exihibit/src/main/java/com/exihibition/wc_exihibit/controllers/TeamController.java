package com.exihibition.wc_exihibit.controllers;

import com.exihibition.wc_exihibit.entities.Team;
import com.exihibition.wc_exihibit.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> getAll() {

        return ResponseEntity.ok(
                teamService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(
            @PathVariable String id
    ) {

        return ResponseEntity.ok(
                teamService.getById(id)
        );
    }
}