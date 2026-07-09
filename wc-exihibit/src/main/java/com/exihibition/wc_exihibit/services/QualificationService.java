package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.dtos.QualifiedTeam;
import com.exihibition.wc_exihibit.dtos.Standing;
import com.exihibition.wc_exihibit.entities.Group;
import com.exihibition.wc_exihibit.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QualificationService {

    private final TournamentService tournamentService;

    public List<QualifiedTeam> getQualifiedTeams(
            Tournament tournament
    ) {

        List<QualifiedTeam> qualifiedTeams =
                new ArrayList<>();

        List<QualifiedTeam> thirdPlaceTeams =
                new ArrayList<>();

        for (Group group : tournament.getGroups()) {

            List<Standing> standings =
                    tournamentService.getStandings(
                            tournament.getId(),
                            group.getId()
                    );

            /*
             * 1st Place
             */
            Standing first =
                    standings.get(0);

            qualifiedTeams.add(
                    createQualifiedTeam(
                            first,
                            group.getName(),
                            1
                    )
            );

            /*
             * 2nd Place
             */
            Standing second =
                    standings.get(1);

            qualifiedTeams.add(
                    createQualifiedTeam(
                            second,
                            group.getName(),
                            2
                    )
            );

            /*
             * 3rd Place
             */
            Standing third =
                    standings.get(2);

            thirdPlaceTeams.add(
                    createQualifiedTeam(
                            third,
                            group.getName(),
                            3
                    )
            );
        }

        /*
         * Sort 3rd Places
         *
         * 1. Points
         * 2. Goal Difference
         * 3. Goals For
         */
        thirdPlaceTeams.sort(
                Comparator
                        .comparing(
                                QualifiedTeam::getPoints
                        )
                        .thenComparing(
                                QualifiedTeam::getGoalDifference
                        )
                        .thenComparing(
                                QualifiedTeam::getGoalsFor
                        )
                        .reversed()
        );

        /*
         * Take best 8 third places
         */
        for (int i = 0; i < 8; i++) {

            qualifiedTeams.add(
                    thirdPlaceTeams.get(i)
            );
        }

        return qualifiedTeams;
    }

    private QualifiedTeam createQualifiedTeam(
            Standing standing,
            String groupName,
            int position
    ) {

        return QualifiedTeam.builder()
                .teamId(
                        standing.getTeamId()
                )
                .groupName(
                        groupName
                )
                .position(
                        position
                )
                .points(
                        standing.getPoints()
                )
                .goalDifference(
                        standing.getGoalsFor()
                                - standing.getGoalsAgainst()
                )
                .goalsFor(
                        standing.getGoalsFor()
                )
                .build();
    }
}