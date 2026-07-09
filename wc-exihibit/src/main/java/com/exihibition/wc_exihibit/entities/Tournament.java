package com.exihibition.wc_exihibit.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("tournaments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournament {

    @Id
    private String id;

    private String email;

    private LocalDate startDate;

    private boolean started;

    private List<Group> groups;

    private List<KnockoutPhase> knockouts;
}