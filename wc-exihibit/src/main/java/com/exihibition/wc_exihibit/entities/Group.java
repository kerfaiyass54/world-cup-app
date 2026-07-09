package com.exihibition.wc_exihibit.entities;

import com.exihibition.wc_exihibit.enums.Continent;
import com.exihibition.wc_exihibit.enums.TeamMoral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    private String id;

    private String name;

    private List<String> teamIds;

    private List<Match> matches;
}
