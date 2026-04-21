package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RunnerupWithoutTitles {

    @Id
    private Long id;
    private String country;

}