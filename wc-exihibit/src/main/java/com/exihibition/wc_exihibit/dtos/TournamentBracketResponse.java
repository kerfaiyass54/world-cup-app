package com.exihibition.wc_exihibit.dtos;

import com.exihibition.wc_exihibit.entities.KnockoutPhase;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TournamentBracketResponse {

    private String tournamentId;

    private List<KnockoutPhase> rounds;
}