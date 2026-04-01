package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cup")
public class Cup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Year is required")
    @Min(value = 1930, message = "World Cup started in 1930")
    @Column(nullable = false, unique = true)
    private Integer year;

    @NotBlank(message = "Host is required")
    @Column(nullable = false)
    private String host;

    @Column
    private String winner;

    @OneToMany(mappedBy = "cup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Participation> participations;
}