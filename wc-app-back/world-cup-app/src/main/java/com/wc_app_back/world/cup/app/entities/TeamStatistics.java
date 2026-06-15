package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team_statistics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team;

    private Integer matchesPlayed;

    private Integer wins;

    private Integer draws;

    private Integer losses;

    private Integer goalsScored;

    private Integer goalsConceded;

    private Integer goalDifference;

    private Double winPercentage;

    private Double avgAttendance;

    private Integer appearances;

}