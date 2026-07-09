package com.exihibition.wc_exihibit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTournamentRequest {

    private String email;

    private LocalDate startDate;

    private List<GroupRequest> groups;
}