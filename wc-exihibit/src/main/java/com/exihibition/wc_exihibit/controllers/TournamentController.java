package com.exihibition.wc_exihibit.controllers;

import com.exihibition.wc_exihibit.dtos.*;
import com.exihibition.wc_exihibit.entities.Group;
import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Tournament;
import com.exihibition.wc_exihibit.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentStatusService
            tournamentStatusService;
    private final TournamentStatisticsService
            tournamentStatisticsService;
    private final MatchCenterService matchCenterService;
    private final TournamentBracketService
            tournamentBracketService;
    private final TeamJourneyService teamJourneyService;
    private final MatchResultService matchResultService;

    @PostMapping(
            "/{tournamentId}/groups/{groupId}/matches/{matchId}/result"
    )
    public ResponseEntity<Void> saveMatchResult(
            @PathVariable String tournamentId,
            @PathVariable String groupId,
            @PathVariable String matchId,
            @RequestBody MatchResultMessage result
    ) {

        matchResultService.processResult(
                result
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{tournamentId}/teams/{teamId}/journey")
    public ResponseEntity<TeamJourneyResponse>
    getTeamJourney(
            @PathVariable String tournamentId,
            @PathVariable String teamId
    ) {

        return ResponseEntity.ok(
                teamJourneyService.getJourney(
                        tournamentId,
                        teamId
                )
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Tournament>>
    getTournamentsByEmail(
            @PathVariable String email
    ) {

        return ResponseEntity.ok(
                tournamentService
                        .getTournamentsByEmail(
                                email
                        )
        );
    }

    @GetMapping("/{id}/details")
    public TournamentDetailsResponse getTournamentDetails(
            @PathVariable String id
    ) {

        return tournamentService
                .getTournamentDetails(id);
    }

    @GetMapping("/{tournamentId}/bracket")
    public ResponseEntity<TournamentBracketResponse>
    getBracket(
            @PathVariable String tournamentId
    ) {

        return ResponseEntity.ok(
                tournamentBracketService
                        .getBracket(
                                tournamentId
                        )
        );
    }

    @GetMapping("/{tournamentId}/matches")
    public ResponseEntity<List<MatchCenterResponse>>
    getMatches(
            @PathVariable String tournamentId
    ) {

        return ResponseEntity.ok(
                matchCenterService.getMatches(
                        tournamentId
                )
        );
    }


    @GetMapping("/{tournamentId}/statistics")
    public ResponseEntity<TournamentStatisticsResponse>
    getStatistics(
            @PathVariable String tournamentId
    ) {

        return ResponseEntity.ok(
                tournamentStatisticsService
                        .getStatistics(
                                tournamentId
                        )
        );
    }

    @GetMapping("/{tournamentId}/status")
    public ResponseEntity<TournamentStatusResponse>
    getStatus(
            @PathVariable String tournamentId
    ) {

        return ResponseEntity.ok(
                tournamentStatusService
                        .getStatus(
                                tournamentId
                        )
        );
    }



    @PostMapping
    public ResponseEntity<Tournament> createTournament(
            @RequestBody CreateTournamentRequest request) {

        Tournament tournament =
                tournamentService.createTournament(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tournament);
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<Tournament> getTournament(
            @PathVariable String tournamentId) {

        return ResponseEntity.ok(
                tournamentService.getTournament(tournamentId)
        );
    }

    @GetMapping("/{tournamentId}/groups")
    public ResponseEntity<List<Group>> getGroups(
            @PathVariable String tournamentId) {

        return ResponseEntity.ok(
                tournamentService.getGroups(tournamentId)
        );
    }

    @GetMapping("/{tournamentId}/groups/{groupId}")
    public ResponseEntity<Group> getGroup(
            @PathVariable String tournamentId,
            @PathVariable String groupId) {

        return ResponseEntity.ok(
                tournamentService.getGroup(
                        tournamentId,
                        groupId
                )
        );
    }

    @PostMapping("/{tournamentId}/start")
    public ResponseEntity<Tournament> startTournament(
            @PathVariable String tournamentId) {

        return ResponseEntity.ok(
                tournamentService.startTournament(tournamentId)
        );
    }

    @PostMapping("/{tournamentId}/groups/{groupId}/teams/{teamId}")
    public ResponseEntity<Tournament> addTeamToGroup(
            @PathVariable String tournamentId,
            @PathVariable String groupId,
            @PathVariable String teamId) {

        return ResponseEntity.ok(
                tournamentService.addTeamToGroup(
                        tournamentId,
                        groupId,
                        teamId
                )
        );
    }

    @GetMapping("/{tournamentId}/groups/{groupId}/matches")
    public ResponseEntity<List<Match>> getGroupMatches(
            @PathVariable String tournamentId,
            @PathVariable String groupId) {

        return ResponseEntity.ok(
                tournamentService.getGroupMatches(
                        tournamentId,
                        groupId
                )
        );
    }

    @GetMapping("/{tournamentId}/groups/{groupId}/standings")
    public ResponseEntity<List<Standing>> getStandings(
            @PathVariable String tournamentId,
            @PathVariable String groupId) {

        return ResponseEntity.ok(
                tournamentService.getStandings(
                        tournamentId,
                        groupId
                )
        );
    }

    @GetMapping("/{tournamentId}/knockouts")
    public ResponseEntity<List<KnockoutPhase>> getKnockoutRounds(
            @PathVariable String tournamentId) {

        return ResponseEntity.ok(
                tournamentService.getKnockoutRounds(
                        tournamentId
                )
        );
    }


}