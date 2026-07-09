package com.exihibition.wc_exihibit.controllers;


import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.services.MatchLaunchService;
import com.exihibition.wc_exihibit.services.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final TournamentService tournamentService;
    private final MatchLaunchService launchService;

    @PostMapping("/{matchId}/simulate")
    public ResponseEntity<String> simulate(
            @PathVariable String matchId
    ) {

        Match match =
                tournamentService
                        .findMatch(matchId);

        launchService.launchMatch(match);

        return ResponseEntity.ok(
                "Simulation launched"
        );
    }


}
