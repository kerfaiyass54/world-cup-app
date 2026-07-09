package com.exihibition.wc_exihibit.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamPreview {

    private String id;

    private String name;

    private Integer ability;

    private String continent;
}