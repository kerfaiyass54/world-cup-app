package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="champion_era_stats")
@Getter
@Setter
public class ChampionEraStats {


    @Id
    private Long id;

    private String era;

    private String country;

    private Integer wins;

}
