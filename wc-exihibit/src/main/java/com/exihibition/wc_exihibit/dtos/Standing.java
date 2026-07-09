package com.exihibition.wc_exihibit.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Standing {

    private String teamId;

    private int played;

    private int won;

    private int draw;

    private int lost;

    private int goalsFor;

    private int goalsAgainst;

    private int points;
}