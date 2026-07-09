package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.dtos.QualifiedTeam;
import com.exihibition.wc_exihibit.dtos.TournamentStatusResponse;
import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentStatusService {

    private final TournamentService tournamentService;

    private final QualificationService qualificationService;

    public TournamentStatusResponse getStatus(
            String tournamentId
    ) {

        Tournament tournament =
                tournamentService.getTournament(
                        tournamentId
                );

        boolean groupsFinished =
                tournamentService
                        .allGroupsFinished(
                                tournament
                        );

        int qualifiedTeams = 0;

        if (groupsFinished) {

            List<QualifiedTeam> teams =
                    qualificationService
                            .getQualifiedTeams(
                                    tournament
                            );

            qualifiedTeams =
                    teams.size();
        }

        String currentStage =
                determineCurrentStage(
                        tournament
                );

        String champion =
                determineChampion(
                        tournament
                );

        return TournamentStatusResponse
                .builder()
                .tournamentId(
                        tournament.getId()
                )
                .started(
                        tournament.isStarted()
                )
                .stage(
                        currentStage
                )
                .groupsFinished(
                        groupsFinished
                )
                .qualifiedTeams(
                        qualifiedTeams
                )
                .championTeamId(
                        champion
                )
                .build();
    }

    private String determineCurrentStage(
            Tournament tournament
    ) {

        if (tournament.getKnockouts() == null
                || tournament.getKnockouts().isEmpty()) {

            return "GROUP_STAGE";
        }

        return tournament.getKnockouts()
                .get(
                        tournament.getKnockouts()
                                .size() - 1
                )
                .getType()
                .name();
    }

    private String determineChampion(
            Tournament tournament
    ) {

        KnockoutPhase finalPhase =
                tournament.getKnockouts()
                        .stream()
                        .filter(
                                phase ->
                                        phase.getType()
                                                .name()
                                                .equals("FINAL")
                        )
                        .findFirst()
                        .orElse(null);

        if (finalPhase == null) {
            return null;
        }

        if (finalPhase.getMatches().isEmpty()) {
            return null;
        }

        Match finalMatch =
                finalPhase.getMatches()
                        .get(0);

        if (!finalMatch.isPlayed()) {
            return null;
        }

        return finalMatch.getWinnerTeamId();
    }
}