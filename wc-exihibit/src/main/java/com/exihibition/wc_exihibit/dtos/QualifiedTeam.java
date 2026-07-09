package com.exihibition.wc_exihibit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualifiedTeam {

    private String teamId;

    private String groupName;

    private Integer position;

    private Integer points;

    private Integer goalDifference;

    private Integer goalsFor;
}