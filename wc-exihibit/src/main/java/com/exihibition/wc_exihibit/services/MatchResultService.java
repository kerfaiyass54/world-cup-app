package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.dtos.MatchResultMessage;
import com.exihibition.wc_exihibit.dtos.QualifiedTeam;
import com.exihibition.wc_exihibit.entities.Group;
import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Tournament;
import com.exihibition.wc_exihibit.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchResultService {

    private final TournamentRepository tournamentRepository;

    private final TournamentService tournamentService;

    private final QualificationService qualificationService;

    private final KnockoutGeneratorService knockoutGeneratorService;

    private final KnockoutProgressionService knockoutProgressionService;

    public void processResult(
            MatchResultMessage result
    ) {

        List<Tournament> tournaments =
                tournamentRepository.findAll();

        for (Tournament tournament : tournaments) {

            boolean matchFound = false;

            /*
             * -------------------------
             * GROUP MATCHES
             * -------------------------
             */
            for (Group group :
                    tournament.getGroups()) {

                for (Match match :
                        group.getMatches()) {

                    if (
                            match.getId()
                                    .equals(
                                            result.getMatchId()
                                    )
                    ) {

                        updateMatch(
                                match,
                                result
                        );

                        matchFound = true;
                        break;
                    }
                }

                if (matchFound) {
                    break;
                }
            }

            /*
             * -------------------------
             * KNOCKOUT MATCHES
             * -------------------------
             */
            if (!matchFound) {

                for (KnockoutPhase phase :
                        tournament.getKnockouts()) {

                    for (Match match :
                            phase.getMatches()) {

                        if (
                                match.getId()
                                        .equals(
                                                result.getMatchId()
                                        )
                        ) {

                            updateMatch(
                                    match,
                                    result
                            );

                            matchFound = true;
                            break;
                        }
                    }

                    if (matchFound) {
                        break;
                    }
                }
            }

            if (!matchFound) {
                continue;
            }

            /*
             * -------------------------
             * GENERATE ROUND OF 32
             * -------------------------
             */
            if (
                    tournamentService
                            .allGroupsFinished(
                                    tournament
                            )
                            &&
                            tournament.getKnockouts()
                                    .isEmpty()
            ) {

                List<QualifiedTeam> qualifiedTeams =
                        qualificationService
                                .getQualifiedTeams(
                                        tournament
                                );

                knockoutGeneratorService
                        .generateRoundOf32(
                                tournament,
                                qualifiedTeams
                        );
            }

            /*
             * -------------------------
             * GENERATE NEXT ROUNDS
             * -------------------------
             */
            for (KnockoutPhase phase :
                    tournament.getKnockouts()) {

                knockoutProgressionService
                        .generateNextRound(
                                tournament,
                                phase
                        );
            }

            tournamentRepository.save(
                    tournament
            );

            return;
        }

        throw new RuntimeException(
                "Match not found : "
                        + result.getMatchId()
        );
    }

    private void updateMatch(
            Match match,
            MatchResultMessage result
    ) {

        match.setPlayed(true);

        match.setGoalsHome(
                result.getHomeGoals()
        );

        match.setGoalsAway(
                result.getAwayGoals()
        );

        match.setWinnerTeamId(
                result.getWinnerTeamId()
        );
    }
}