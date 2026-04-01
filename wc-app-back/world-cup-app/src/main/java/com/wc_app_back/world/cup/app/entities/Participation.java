package com.wc_app_back.world.cup.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "participation",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_nation", "id_cup"})
)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nation is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nation", nullable = false)
    private Nation nation;

    @NotNull(message = "Cup is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cup", nullable = false)
    private Cup cup;
}