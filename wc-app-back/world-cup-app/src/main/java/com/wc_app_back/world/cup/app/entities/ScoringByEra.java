package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="scoring_by_era")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoringByEra {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String era;

    private Double avgGoalsPerGame;

}
