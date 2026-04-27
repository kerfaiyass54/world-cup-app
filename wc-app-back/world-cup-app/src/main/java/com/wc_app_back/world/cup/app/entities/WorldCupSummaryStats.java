package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "worldcup_summary_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorldCupSummaryStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalEditions;
    private Integer totalChampions;
    private Integer totalHosts;

    private Double highestAvgGoals;
    private Integer highestAvgGoalsYear;
}