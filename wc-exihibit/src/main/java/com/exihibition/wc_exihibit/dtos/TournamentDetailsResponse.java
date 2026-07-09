package com.exihibition.wc_exihibit.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TournamentDetailsResponse {

    private String id;

    private String email;

    private List<GroupPreview> groups;
}