package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.dtos.Standing;
import com.exihibition.wc_exihibit.dtos.TeamJourneyMatchDto;
import com.exihibition.wc_exihibit.dtos.TeamJourneyResponse;
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
public class TeamJourneyService {

    private final TournamentService tournamentService;

    public TeamJourneyResponse getJourney(
            String tournamentId,
            String teamId
    ) {

        Tournament tournament =
                tournamentService.getTournament(
                        tournamentId
                );

        String groupName = null;
        Integer groupPosition = null;
        Integer groupPoints = null;

        boolean qualified = false;
        boolean champion = false;
        String eliminatedIn = null;

        List<TeamJourneyMatchDto> matches =
                new ArrayList<>();

        /*
         * GROUP STAGE
         */
        for (Group group :
                tournament.getGroups()) {

            if (!group.getTeamIds().contains(teamId)) {
                continue;
            }

            groupName = group.getName();

            List<Standing> standings =
                    tournamentService.getStandings(
                            tournamentId,
                            group.getId()
                    );

            for (int i = 0; i < standings.size(); i++) {

                Standing standing =
                        standings.get(i);

                if (standing.getTeamId()
                        .equals(teamId)) {

                    groupPosition = i + 1;
                    groupPoints =
                            standing.getPoints();

                    qualified =
                            groupPosition <= 2
                                    || groupPosition == 3;

                    break;
                }
            }

            for (Match match :
                    group.getMatches()) {

                if (!containsTeam(match, teamId)) {
                    continue;
                }

                matches.add(
                        buildMatch(
                                match,
                                teamId,
                                group.getName(),
                                null
                        )
                );
            }
        }

        /*
         * KNOCKOUTS
         */
        for (KnockoutPhase phase :
                tournament.getKnockouts()) {

            for (Match match :
                    phase.getMatches()) {

                if (!containsTeam(match, teamId)) {
                    continue;
                }

                matches.add(
                        buildMatch(
                                match,
                                teamId,
                                null,
                                phase.getType()
                        )
                );

                if (match.isPlayed()) {

                    boolean eliminated =
                            !teamId.equals(
                                    match.getWinnerTeamId()
                            );

                    if (eliminated) {

                        eliminatedIn =
                                phase.getType()
                                        .name();
                    }

                    if (
                            phase.getType().name()
                                    .equals("FINAL")
                                    &&
                                    teamId.equals(
                                            match.getWinnerTeamId()
                                    )
                    ) {

                        champion = true;
                    }
                }
            }
        }

        return TeamJourneyResponse
                .builder()
                .teamId(teamId)
                .groupName(groupName)
                .groupPosition(groupPosition)
                .groupPoints(groupPoints)
                .qualified(qualified)
                .champion(champion)
                .eliminatedIn(eliminatedIn)
                .matches(matches)
                .build();
    }

    private boolean containsTeam(
            Match match,
            String teamId
    ) {

        return match.getTeamHomeId()
                .equals(teamId)
                ||
                match.getTeamAwayId()
                        .equals(teamId);
    }

    private TeamJourneyMatchDto buildMatch(
            Match match,
            String teamId,
            String groupName,
            com.exihibition.wc_exihibit.enums.KnockoutType knockoutType
    ) {

        boolean home =
                match.getTeamHomeId()
                        .equals(teamId);

        int goalsFor =
                home
                        ? match.getGoalsHome()
                        : match.getGoalsAway();

        int goalsAgainst =
                home
                        ? match.getGoalsAway()
                        : match.getGoalsHome();

        String opponent =
                home
                        ? match.getTeamAwayId()
                        : match.getTeamHomeId();

        return TeamJourneyMatchDto
                .builder()
                .matchId(match.getId())
                .opponentId(opponent)
                .goalsFor(goalsFor)
                .goalsAgainst(goalsAgainst)
                .won(goalsFor > goalsAgainst)
                .draw(goalsFor == goalsAgainst)
                .lost(goalsFor < goalsAgainst)
                .groupName(groupName)
                .knockoutType(knockoutType)
                .build();
    }
}