package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="top3_consistency_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Top3ConsistencyStats {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String country;

    private Double consistencyScore;

}
