package com.wcpredicitons.wc_predict.dto;

import lombok.Data;

@Data
public class PredictionRequest {

    private String email;

    private String champion;

    private String thirdPlace;

    private String groupsJson;

    private String bracketJson;
}