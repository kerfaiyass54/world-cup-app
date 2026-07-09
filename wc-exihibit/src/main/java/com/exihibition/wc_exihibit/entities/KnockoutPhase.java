package com.exihibition.wc_exihibit.entities;

import com.exihibition.wc_exihibit.enums.KnockoutType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnockoutPhase {

    private String id;

    private KnockoutType type;

    private List<Match> matches;
}