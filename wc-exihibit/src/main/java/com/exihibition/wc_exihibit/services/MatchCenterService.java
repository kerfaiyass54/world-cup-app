package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.dtos.MatchCenterResponse;
import com.exihibition.wc_exihibit.entities.Group;
import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchCenterService {

    private final TournamentService tournamentService;

    public List<MatchCenterResponse> getMatches(
            String tournamentId
    ) {

        Tournament tournament =
                tournamentService.getTournament(
                        tournamentId
                );

        List<MatchCenterResponse> response =
                new ArrayList<>();

        /*
         * GROUP MATCHES
         */
        for (Group group :
                tournament.getGroups()) {

            for (Match match :
                    group.getMatches()) {

                response.add(
                        MatchCenterResponse.builder()
                                .matchId(
                                        match.getId()
                                )
                                .teamHomeId(
                                        match.getTeamHomeId()
                                )
                                .teamAwayId(
                                        match.getTeamAwayId()
                                )
                                .goalsHome(
                                        match.getGoalsHome()
                                )
                                .goalsAway(
                                        match.getGoalsAway()
                                )
                                .played(
                                        match.isPlayed()
                                )
                                .groupName(
                                        group.getName()
                                )
                                .winnerTeamId(
                                        match.getWinnerTeamId()
                                )
                                .build()
                );
            }
        }

        /*
         * KNOCKOUT MATCHES
         */
        for (KnockoutPhase phase :
                tournament.getKnockouts()) {

            for (Match match :
                    phase.getMatches()) {

                response.add(
                        MatchCenterResponse.builder()
                                .matchId(
                                        match.getId()
                                )
                                .teamHomeId(
                                        match.getTeamHomeId()
                                )
                                .teamAwayId(
                                        match.getTeamAwayId()
                                )
                                .goalsHome(
                                        match.getGoalsHome()
                                )
                                .goalsAway(
                                        match.getGoalsAway()
                                )
                                .played(
                                        match.isPlayed()
                                )
                                .knockoutType(
                                        phase.getType()
                                )
                                .winnerTeamId(
                                        match.getWinnerTeamId()
                                )
                                .build()
                );
            }
        }

        return response;
    }
}