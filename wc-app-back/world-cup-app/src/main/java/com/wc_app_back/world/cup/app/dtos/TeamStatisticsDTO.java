package com.wc_app_back.world.cup.app.dtos;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamStatisticsDTO {

    private String team;

    private Integer matches_played;

    private Integer wins;

    private Integer draws;

    private Integer losses;

    private Integer goals_scored;

    private Integer goals_conceded;

    private Integer goal_difference;

    private Double win_percentage;

    private Double avg_attendance;

    private Integer appearances;

    // getters and setters
}