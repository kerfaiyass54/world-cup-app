package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AvgAttendanceByHost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String host;

    private Integer attendanceAvg;
}