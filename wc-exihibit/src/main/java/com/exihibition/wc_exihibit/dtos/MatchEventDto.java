package com.exihibition.wc_exihibit.dtos;

import lombok.Data;

@Data
public class MatchEventDto {

    private String matchId;

    private Integer minute;

    private String eventType;

    private String description;

    private String teamId;

    private String score;
}