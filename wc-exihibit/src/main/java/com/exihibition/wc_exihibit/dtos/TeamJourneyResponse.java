package com.exihibition.wc_exihibit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamJourneyResponse {

    private String teamId;

    private String groupName;

    private Integer groupPosition;

    private Integer groupPoints;

    private boolean qualified;

    private boolean champion;

    private String eliminatedIn;

    private List<TeamJourneyMatchDto> matches;
}