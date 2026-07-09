package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.dtos.TournamentBracketResponse;
import com.exihibition.wc_exihibit.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentBracketService {

    private final TournamentService
            tournamentService;

    public TournamentBracketResponse getBracket(
            String tournamentId
    ) {

        Tournament tournament =
                tournamentService
                        .getTournament(
                                tournamentId
                        );

        return TournamentBracketResponse
                .builder()
                .tournamentId(
                        tournament.getId()
                )
                .rounds(
                        tournament.getKnockouts()
                )
                .build();
    }
}