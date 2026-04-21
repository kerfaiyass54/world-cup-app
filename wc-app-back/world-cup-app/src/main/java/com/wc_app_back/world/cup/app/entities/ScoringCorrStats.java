package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="scoring_corr_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoringCorrStats {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String metric;

    private Double value;

}
