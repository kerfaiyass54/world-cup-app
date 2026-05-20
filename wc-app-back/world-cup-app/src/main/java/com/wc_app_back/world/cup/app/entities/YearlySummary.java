package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class YearlySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    private String host;

    private String champion;

    private String runnerUp;

    private Integer matches;

    private String topScorerPlayer;

    private Integer topScorerGoals;
}