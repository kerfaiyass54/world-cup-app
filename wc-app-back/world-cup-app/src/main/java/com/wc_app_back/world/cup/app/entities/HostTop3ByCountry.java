package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="host_top3_by_country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostTop3ByCountry {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String country;

    private Integer top3Count;

}
