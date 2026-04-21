package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="top3_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Top3Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String country;

    private Integer appearances;

}
