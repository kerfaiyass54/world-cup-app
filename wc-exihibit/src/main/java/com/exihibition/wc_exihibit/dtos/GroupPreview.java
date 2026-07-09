package com.exihibition.wc_exihibit.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupPreview {

    private String id;

    private String name;

    private List<TeamPreview> teams;

    private Integer matchesCount;
}