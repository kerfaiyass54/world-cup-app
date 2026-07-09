package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.dtos.TournamentStatisticsResponse;
import com.exihibition.wc_exihibit.entities.Group;
import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TournamentStatisticsService {

    private final TournamentService tournamentService;

    public TournamentStatisticsResponse getStatistics(
            String tournamentId
    ) {

        Tournament tournament =
                tournamentService.getTournament(
                        tournamentId
                );

        int totalMatches = 0;
        int playedMatches = 0;

        int totalGoals = 0;

        int homeGoals = 0;
        int awayGoals = 0;

        Map<String, Integer> teamGoals =
                new HashMap<>();

        /*
         * GROUP MATCHES
         */
        for (Group group :
                tournament.getGroups()) {

            totalMatches +=
                    group.getMatches().size();

            for (Match match :
                    group.getMatches()) {

                if (!match.isPlayed()) {
                    continue;
                }

                playedMatches++;

                totalGoals +=
                        match.getGoalsHome()
                                + match.getGoalsAway();

                homeGoals +=
                        match.getGoalsHome();

                awayGoals +=
                        match.getGoalsAway();

                teamGoals.merge(
                        match.getTeamHomeId(),
                        match.getGoalsHome(),
                        Integer::sum
                );

                teamGoals.merge(
                        match.getTeamAwayId(),
                        match.getGoalsAway(),
                        Integer::sum
                );
            }
        }

        /*
         * KNOCKOUT MATCHES
         */
        for (KnockoutPhase phase :
                tournament.getKnockouts()) {

            totalMatches +=
                    phase.getMatches().size();

            for (Match match :
                    phase.getMatches()) {

                if (!match.isPlayed()) {
                    continue;
                }

                playedMatches++;

                totalGoals +=
                        match.getGoalsHome()
                                + match.getGoalsAway();

                homeGoals +=
                        match.getGoalsHome();

                awayGoals +=
                        match.getGoalsAway();

                teamGoals.merge(
                        match.getTeamHomeId(),
                        match.getGoalsHome(),
                        Integer::sum
                );

                teamGoals.merge(
                        match.getTeamAwayId(),
                        match.getGoalsAway(),
                        Integer::sum
                );
            }
        }

        String topScoringTeam = null;
        int maxGoals = 0;

        for (Map.Entry<String, Integer> entry :
                teamGoals.entrySet()) {

            if (entry.getValue() > maxGoals) {

                maxGoals =
                        entry.getValue();

                topScoringTeam =
                        entry.getKey();
            }
        }

        double averageGoals = 0.0;

        if (playedMatches > 0) {

            averageGoals =
                    (double) totalGoals
                            / playedMatches;
        }

        return TournamentStatisticsResponse
                .builder()
                .totalMatches(totalMatches)
                .playedMatches(playedMatches)
                .totalGoals(totalGoals)
                .averageGoalsPerMatch(
                        averageGoals
                )
                .topScoringTeamId(
                        topScoringTeam
                )
                .topScoringTeamGoals(
                        maxGoals
                )
                .homeGoals(homeGoals)
                .awayGoals(awayGoals)
                .build();
    }
}