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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nation")
public class Nation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Continent is required")
    @Column(nullable = false)
    private String continent;

    @NotNull(message = "Number of participations is required")
    @Min(value = 0, message = "Number of participations cannot be negative")
    @Column(name = "number_participation", nullable = false)
    private Integer numberParticipation = 0;

    @Min(value = 1930, message = "World Cup started in 1930")
    @Column(name = "last_participation")
    private Integer lastParticipation;

    @OneToMany(mappedBy = "nation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Participation> participations;
}