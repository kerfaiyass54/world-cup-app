package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="champion_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChampionStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String country;

    @Column(nullable=false)
    private Integer wins;

}