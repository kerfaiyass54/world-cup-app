package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Tournament;
import com.exihibition.wc_exihibit.enums.KnockoutType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class KnockoutProgressionService {

    private void createThirdPlaceMatch(
            Tournament tournament,
            KnockoutPhase semiFinals
    ) {

        boolean exists =
                tournament.getKnockouts()
                        .stream()
                        .anyMatch(
                                phase ->
                                        phase.getType()
                                                ==
                                                KnockoutType.THIRD_PLACE
                        );

        if (exists) {
            return;
        }

        List<String> losers =
                getLosers(
                        semiFinals
                );

        if (losers.size() != 2) {
            return;
        }

        Match match =
                Match.builder()
                        .id(
                                UUID.randomUUID()
                                        .toString()
                        )
                        .tournamentId(
                                tournament.getId()
                        )
                        .teamHomeId(
                                losers.get(0)
                        )
                        .teamAwayId(
                                losers.get(1)
                        )
                        .played(false)
                        .goalsHome(0)
                        .goalsAway(0)
                        .build();

        KnockoutPhase thirdPlace =
                KnockoutPhase.builder()
                        .id(
                                UUID.randomUUID()
                                        .toString()
                        )
                        .type(
                                KnockoutType.THIRD_PLACE
                        )
                        .matches(
                                List.of(match)
                        )
                        .build();

        tournament.getKnockouts()
                .add(
                        thirdPlace
                );
    }

    public void generateNextRound(
            Tournament tournament,
            KnockoutPhase currentPhase
    ) {

        if (!isPhaseFinished(currentPhase)) {
            return;
        }

        if (
                currentPhase.getType()
                        == KnockoutType.SEMI_FINAL
        ) {

            createThirdPlaceMatch(
                    tournament,
                    currentPhase
            );
        }

        KnockoutType nextType =
                getNextPhaseType(
                        currentPhase.getType()
                );

        if (nextType == null) {
            return;
        }

        boolean alreadyExists =
                tournament.getKnockouts()
                        .stream()
                        .anyMatch(
                                phase ->
                                        phase.getType()
                                                == nextType
                        );

        if (alreadyExists) {
            return;
        }

        List<Match> currentMatches =
                currentPhase.getMatches();

        List<Match> nextMatches =
                new ArrayList<>();

        for (
                int i = 0;
                i < currentMatches.size();
                i += 2
        ) {

            Match first =
                    currentMatches.get(i);

            Match second =
                    currentMatches.get(i + 1);

            if (
                    first.getWinnerTeamId() == null
                            ||
                            second.getWinnerTeamId() == null
            ) {
                return;
            }

            Match nextMatch =
                    Match.builder()
                            .id(
                                    UUID.randomUUID()
                                            .toString()
                            )
                            .tournamentId(
                                    tournament.getId()
                            )
                            .teamHomeId(
                                    first.getWinnerTeamId()
                            )
                            .teamAwayId(
                                    second.getWinnerTeamId()
                            )
                            .played(false)
                            .goalsHome(0)
                            .goalsAway(0)
                            .build();

            nextMatches.add(
                    nextMatch
            );

        }

        KnockoutPhase nextPhase =
                KnockoutPhase.builder()
                        .id(UUID.randomUUID().toString())
                        .type(nextType)
                        .matches(nextMatches)
                        .build();

        tournament.getKnockouts()
                .add(nextPhase);
    }

    private List<String> getLosers(
            KnockoutPhase phase
    ) {

        List<String> losers =
                new ArrayList<>();

        for (Match match :
                phase.getMatches()) {

            if (
                    match.getWinnerTeamId()
                            .equals(
                                    match.getTeamHomeId()
                            )
            ) {

                losers.add(
                        match.getTeamAwayId()
                );

            } else {

                losers.add(
                        match.getTeamHomeId()
                );
            }
        }

        return losers;
    }

    private boolean isPhaseFinished(
            KnockoutPhase phase
    ) {

        return phase.getMatches()
                .stream()
                .allMatch(Match::isPlayed);
    }

    private List<String> getWinners(
            KnockoutPhase phase
    ) {

        List<String> winners =
                new ArrayList<>();

        for (Match match :
                phase.getMatches()) {

            if (match.getWinnerTeamId() != null) {

                winners.add(
                        match.getWinnerTeamId()
                );
            }
        }

        return winners;
    }

    private KnockoutType getNextPhaseType(
            KnockoutType current
    ) {

        return switch (current) {

            case ROUND_32 ->
                    KnockoutType.ROUND_16;

            case ROUND_16 ->
                    KnockoutType.QUARTER_FINAL;

            case QUARTER_FINAL ->
                    KnockoutType.SEMI_FINAL;

            case SEMI_FINAL ->
                    KnockoutType.FINAL;

            default ->
                    null;
        };
    }
}