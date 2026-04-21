package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="teams_growth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamsGrowth {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    private Integer teams;

}
