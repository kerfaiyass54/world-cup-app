package com.exihibition.wc_exihibit.entities;

import com.exihibition.wc_exihibit.enums.Continent;
import com.exihibition.wc_exihibit.enums.TeamMoral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    private String id;

    private String name;

    private Integer ability;

    private TeamMoral moral;

    private Continent continent;
}