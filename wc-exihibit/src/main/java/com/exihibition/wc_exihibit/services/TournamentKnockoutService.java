package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import com.exihibition.wc_exihibit.entities.Match;
import com.exihibition.wc_exihibit.entities.Tournament;
import org.springframework.stereotype.Service;

@Service
public class TournamentKnockoutService {

    public KnockoutPhase findPhaseByMatch(
            Tournament tournament,
            String matchId
    ) {

        if (tournament.getKnockouts() == null) {
            return null;
        }

        return tournament.getKnockouts()
                .stream()
                .filter(
                        phase ->
                                phase.getMatches()
                                        .stream()
                                        .anyMatch(
                                                match ->
                                                        match.getId()
                                                                .equals(matchId)
                                        )
                )
                .findFirst()
                .orElse(null);
    }

    public Match findMatch(
            KnockoutPhase phase,
            String matchId
    ) {

        if (phase == null) {
            return null;
        }

        return phase.getMatches()
                .stream()
                .filter(
                        match ->
                                match.getId()
                                        .equals(matchId)
                )
                .findFirst()
                .orElse(null);
    }
}