package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.dtos.QualifiedTeam;
import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Tournament;
import com.exihibition.wc_exihibit.enums.KnockoutType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KnockoutGeneratorService {

    public KnockoutPhase generateRoundOf32(
            Tournament tournament,
            List<QualifiedTeam> qualifiedTeams
    ) {

        if (qualifiedTeams.size() != 32) {

            throw new RuntimeException(
                    "Round of 32 requires exactly 32 teams. Found: "
                            + qualifiedTeams.size()
            );
        }

        List<QualifiedTeam> winners =
                new ArrayList<>();

        List<QualifiedTeam> runnersUp =
                new ArrayList<>();

        List<QualifiedTeam> thirdPlaced =
                new ArrayList<>();

        for (QualifiedTeam team : qualifiedTeams) {

            if (team.getPosition() == 1) {

                winners.add(team);

            } else if (team.getPosition() == 2) {

                runnersUp.add(team);

            } else {

                thirdPlaced.add(team);
            }
        }

        List<Match> matches =
                new ArrayList<>();

        List<QualifiedTeam> top8Winners =
                winners.subList(0, 8);

        List<QualifiedTeam> bottom4Winners =
                winners.subList(8, 12);

        for (QualifiedTeam winner : top8Winners) {

            QualifiedTeam opponent =
                    findOpponent(
                            winner,
                            thirdPlaced
                    );

            if (opponent == null) {
                continue;
            }

            thirdPlaced.remove(opponent);

            matches.add(
                    createMatch(
                            tournament,
                            winner,
                            opponent
                    )
            );
        }

        for (QualifiedTeam winner : bottom4Winners) {

            QualifiedTeam opponent =
                    findOpponent(
                            winner,
                            runnersUp
                    );

            if (opponent == null) {
                continue;
            }

            runnersUp.remove(opponent);

            matches.add(
                    createMatch(
                            tournament,
                            winner,
                            opponent
                    )
            );
        }
        while (
                runnersUp.size() >= 2
        ) {

            QualifiedTeam home =
                    runnersUp.remove(0);

            QualifiedTeam away =
                    findOpponent(
                            home,
                            runnersUp
                    );

            if (away == null) {

                away =
                        runnersUp.get(0);
            }

            runnersUp.remove(
                    away
            );

            matches.add(
                    createMatch(
                            tournament,
                            home,
                            away
                    )
            );
        }

        KnockoutPhase round32 =
                KnockoutPhase.builder()
                        .id(
                                UUID.randomUUID()
                                        .toString()
                        )
                        .type(
                                KnockoutType.ROUND_32
                        )
                        .matches(matches)
                        .build();

        tournament
                .getKnockouts()
                .add(round32);

        return round32;
    }

    private QualifiedTeam findOpponent(
            QualifiedTeam team,
            List<QualifiedTeam> candidates
    ) {

        for (
                QualifiedTeam candidate :
                candidates
        ) {

            if (
                    !candidate
                            .getGroupName()
                            .equals(
                                    team.getGroupName()
                            )
            ) {

                return candidate;
            }
        }

        return null;
    }

    private Match createMatch(
            Tournament tournament,
            QualifiedTeam home,
            QualifiedTeam away
    ) {

        return Match.builder()
                .id(
                        UUID.randomUUID()
                                .toString()
                )
                .teamHomeId(
                        home.getTeamId()
                )
                .teamAwayId(
                        away.getTeamId()
                )
                .tournamentId(
                        tournament.getId()
                )
                .played(false)
                .goalsHome(0)
                .goalsAway(0)
                .build();
    }
}