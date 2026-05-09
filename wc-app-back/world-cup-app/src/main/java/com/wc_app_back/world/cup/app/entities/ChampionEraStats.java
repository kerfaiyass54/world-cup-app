package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="champion_era_stats")
@Getter
@Setter
public class ChampionEraStats {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String era;

    private String country;

    private Integer wins;

}
