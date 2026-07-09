package com.exihibition.wc_exihibit.services;


import com.exihibition.wc_exihibit.dtos.MatchSimulationRequest;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Team;
import com.exihibition.wc_exihibit.kafka.MatchSimulationProducer;
import com.exihibition.wc_exihibit.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchLaunchService {

    private final TeamRepository teamRepository;
    private final MatchSimulationProducer producer;

    public void launchMatch(
            Match match
    ) {

        Team home =
                teamRepository.findById(
                        match.getTeamHomeId()
                ).orElseThrow();

        Team away =
                teamRepository.findById(
                        match.getTeamAwayId()
                ).orElseThrow();

        MatchSimulationRequest request =
                MatchSimulationRequest.builder()
                        .matchId(match.getId())
                        .tournamentId(match.getTournamentId())
                        .homeTeamId(home.getId())
                        .awayTeamId(away.getId())
                        .homeTeamName(home.getName())
                        .awayTeamName(away.getName())
                        .homeAbility(home.getAbility())
                        .awayAbility(away.getAbility())
                        .homeMorale(home.getMoral().name())
                        .awayMorale(away.getMoral().name())
                        .homeFormation("4-3-3")
                        .awayFormation("4-3-3")
                        .homeTactic("BALANCED")
                        .awayTactic("BALANCED")
                        .isKnockout(
                                match.getKnockoutId() != null
                        )
                        .build();

        producer.simulateMatch(request);
    }
}