package com.exihibition.wc_exihibit.services;


import com.exihibition.wc_exihibit.dtos.*;
import com.exihibition.wc_exihibit.entities.*;
import com.exihibition.wc_exihibit.enums.Continent;
import com.exihibition.wc_exihibit.repositories.TeamRepository;
import com.exihibition.wc_exihibit.repositories.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    public Tournament createTournament(
            CreateTournamentRequest request
    ) {

        Tournament tournament =
                Tournament.builder()
                        .email(
                                request.getEmail()
                        )
                        .startDate(
                                request.getStartDate()
                        )
                        .started(false)
                        .groups(
                                new ArrayList<>()
                        )
                        .knockouts(
                                new ArrayList<>()
                        )
                        .build();

        tournament =
                tournamentRepository.save(
                        tournament
                );

        List<Group> groups =
                new ArrayList<>();

        for (
                GroupRequest groupRequest :
                request.getGroups()
        ) {

            String groupId =
                    UUID.randomUUID()
                            .toString();

            Group group =
                    Group.builder()
                            .id(groupId)
                            .name(groupRequest.getName())
                            .teamIds(
                                    groupRequest.getTeamIds()
                            )
                            .matches(
                                    createGroupMatches(
                                            tournament.getId(),
                                            groupId,
                                            groupRequest.getTeamIds()
                                    )
                            )
                            .build();

            groups.add(group);
        }

        tournament.setGroups(
                groups
        );

        return tournamentRepository.save(
                tournament
        );
    }

    public Map<String, Team> getTeamsMap() {

        return teamRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Team::getId,
                        team -> team
                ));
    }

    public TournamentDetailsResponse getTournamentDetails(
            String tournamentId
    ) {

        Tournament tournament =
                getTournament(tournamentId);

        Map<String, Team> teamsMap =
                getTeamsMap();

        List<GroupPreview> groups =
                tournament.getGroups()
                        .stream()
                        .map(group -> {

                            List<TeamPreview> teams =
                                    group.getTeamIds()
                                            .stream()
                                            .map(id -> {

                                                Team team =
                                                        teamsMap.get(id);

                                                return TeamPreview.builder()
                                                        .id(team.getId())
                                                        .name(team.getName())
                                                        .ability(team.getAbility())
                                                        .continent(team.getContinent().name())
                                                        .build();
                                            })
                                            .toList();

                            return GroupPreview.builder()
                                    .id(group.getId())
                                    .name(group.getName())
                                    .teams(teams)
                                    .matchesCount(
                                            group.getMatches().size()
                                    )
                                    .build();
                        })
                        .toList();

        return TournamentDetailsResponse.builder()
                .id(tournament.getId())
                .email(tournament.getEmail())
                .groups(groups)
                .build();
    }

    public Tournament getTournament(String tournamentId) {

        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() ->
                        new RuntimeException("Tournament not found"));
    }

    public List<Group> getGroups(String tournamentId) {

        return getTournament(tournamentId).getGroups();
    }

    public Group getGroup(
            String tournamentId,
            String groupId) {

        Tournament tournament =
                getTournament(tournamentId);

        return tournament.getGroups()
                .stream()
                .filter(g -> g.getId().equals(groupId))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Group not found"));
    }

    public Tournament startTournament(
            String tournamentId) {

        Tournament tournament =
                getTournament(tournamentId);

        for (Group group : tournament.getGroups()) {

            if (group.getTeamIds().size() != 4) {
                throw new RuntimeException(
                        "Group "
                                + group.getName()
                                + " must contain 4 teams");
            }
        }

        tournament.setStarted(true);

        return tournamentRepository.save(tournament);
    }

    public Tournament addTeamToGroup(
            String tournamentId,
            String groupId,
            String teamId) {

        Tournament tournament =
                getTournament(tournamentId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new RuntimeException("Team not found"));

        Group group = getGroup(
                tournamentId,
                groupId);

        if (group.getTeamIds().size() >= 4) {
            throw new RuntimeException(
                    "Group already contains 4 teams");
        }

        if (group.getTeamIds().contains(teamId)) {
            throw new RuntimeException(
                    "Team already exists in group");
        }

        List<Team> existingTeams =
                teamRepository.findAllById(
                        group.getTeamIds());

        validateGroupRules(
                team,
                existingTeams);

        group.getTeamIds().add(teamId);

        if (group.getTeamIds().size() == 4
                && group.getMatches().isEmpty()) {

            group.setMatches(
                    createGroupMatches(
                            tournament.getId(),
                            group.getId(),
                            group.getTeamIds()
                    )
            );
        }

        return tournamentRepository.save(tournament);
    }

    private void validateGroupRules(
            Team newTeam,
            List<Team> currentTeams) {

        long europeCount =
                currentTeams.stream()
                        .filter(t ->
                                t.getContinent()
                                        == Continent.EUROPE)
                        .count();

        long sameContinent =
                currentTeams.stream()
                        .filter(t ->
                                t.getContinent()
                                        == newTeam.getContinent())
                        .count();

        if (newTeam.getContinent()
                == Continent.EUROPE) {

            if (europeCount >= 2) {
                throw new RuntimeException(
                        "Maximum 2 Europe teams allowed");
            }

        } else {

            if (sameContinent >= 1) {
                throw new RuntimeException(
                        "Only 1 team from "
                                + newTeam.getContinent()
                                + " allowed");
            }
        }
    }

    private List<Match> createGroupMatches(
            String tournamentId,
            String groupId,
            List<String> teamIds) {

        List<Match> matches =
                new ArrayList<>();

        for (int i = 0; i < teamIds.size(); i++) {

            for (int j = i + 1;
                 j < teamIds.size();
                 j++) {

                matches.add(
                        Match.builder()
                                .id(UUID.randomUUID().toString())
                                .teamHomeId(teamIds.get(i))
                                .teamAwayId(teamIds.get(j))
                                .tournamentId(tournamentId)
                                .groupId(groupId)
                                .played(false)
                                .goalsHome(0)
                                .goalsAway(0)
                                .build()
                );
            }
        }

        return matches;
    }

    public List<Match> getGroupMatches(
            String tournamentId,
            String groupId) {

        return getGroup(
                tournamentId,
                groupId)
                .getMatches();
    }

    public List<Standing> getStandings(
            String tournamentId,
            String groupId) {

        Group group =
                getGroup(
                        tournamentId,
                        groupId);

        Map<String, Standing> table =
                new HashMap<>();

        for (String teamId :
                group.getTeamIds()) {

            table.put(
                    teamId,
                    new Standing(
                            teamId,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0
                    )
            );
        }

        for (Match match :
                group.getMatches()) {

            if (!match.isPlayed()) {
                continue;
            }

            Standing home =
                    table.get(
                            match.getTeamHomeId());

            Standing away =
                    table.get(
                            match.getTeamAwayId());

            home.setPlayed(
                    home.getPlayed() + 1);

            away.setPlayed(
                    away.getPlayed() + 1);

            home.setGoalsFor(
                    home.getGoalsFor()
                            + match.getGoalsHome());

            home.setGoalsAgainst(
                    home.getGoalsAgainst()
                            + match.getGoalsAway());

            away.setGoalsFor(
                    away.getGoalsFor()
                            + match.getGoalsAway());

            away.setGoalsAgainst(
                    away.getGoalsAgainst()
                            + match.getGoalsHome());

            if (match.getGoalsHome()
                    > match.getGoalsAway()) {

                home.setWon(
                        home.getWon() + 1);

                away.setLost(
                        away.getLost() + 1);

                home.setPoints(
                        home.getPoints() + 3);

            } else if (match.getGoalsAway()
                    > match.getGoalsHome()) {

                away.setWon(
                        away.getWon() + 1);

                home.setLost(
                        home.getLost() + 1);

                away.setPoints(
                        away.getPoints() + 3);

            } else {

                home.setDraw(
                        home.getDraw() + 1);

                away.setDraw(
                        away.getDraw() + 1);

                home.setPoints(
                        home.getPoints() + 1);

                away.setPoints(
                        away.getPoints() + 1);
            }
        }

        return table.values()
                .stream()
                .sorted((a, b) -> {

                    if (b.getPoints() != a.getPoints()) {
                        return b.getPoints()
                                - a.getPoints();
                    }

                    int gdA =
                            a.getGoalsFor()
                                    - a.getGoalsAgainst();

                    int gdB =
                            b.getGoalsFor()
                                    - b.getGoalsAgainst();

                    if (gdB != gdA) {
                        return gdB - gdA;
                    }

                    return b.getGoalsFor()
                            - a.getGoalsFor();
                })
                .collect(Collectors.toList());
    }

    public List<KnockoutPhase> getKnockoutRounds(
            String tournamentId) {

        return getTournament(tournamentId)
                .getKnockouts();
    }

    public Match findMatch(String matchId) {

        List<Tournament> tournaments =
                tournamentRepository.findAll();

        for (Tournament tournament : tournaments) {

            // Group Matches
            for (Group group : tournament.getGroups()) {

                for (Match match : group.getMatches()) {

                    if (match.getId().equals(matchId)) {
                        return match;
                    }
                }
            }

            // Knockout Matches
            for (KnockoutPhase phase :
                    tournament.getKnockouts()) {

                for (Match match :
                        phase.getMatches()) {

                    if (match.getId().equals(matchId)) {
                        return match;
                    }
                }
            }
        }

        throw new RuntimeException(
                "Match not found: " + matchId
        );
    }

    public List<Tournament> getTournamentsByEmail(
            String email
    ) {

        return tournamentRepository.findByEmail(
                email
        );
    }

    private List<Group> buildGroups(
            List<GroupRequest> requests
    ) {

        List<Group> groups =
                new ArrayList<>();

        if (requests == null) {
            return groups;
        }

        for (GroupRequest request :
                requests) {

            String groupId =
                    UUID.randomUUID()
                            .toString();

            Group group =
                    Group.builder()
                            .id(groupId)
                            .name(request.getName())
                            .teamIds(
                                    request.getTeamIds()
                            )
                            .matches(
                                    new ArrayList<>()
                            )
                            .build();

            if (
                    request.getTeamIds() != null
                            &&
                            request.getTeamIds().size() == 4
            ) {

                group.setMatches(
                        createGroupMatches(
                                null,
                                groupId,
                                request.getTeamIds()
                        )
                );
            }

            groups.add(group);
        }

        return groups;
    }



    

    public boolean allGroupsFinished(
            Tournament tournament
    ) {

        return tournament.getGroups()
                .stream()
                .allMatch(group ->
                        group.getMatches()
                                .stream()
                                .allMatch(Match::isPlayed)
                );
    }
}