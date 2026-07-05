package com.wcpredicitons.wc_predict.model;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "predictions")
public class Prediction {

    @Id
    private String id;

    private String email;

    private String champion;

    private String thirdPlace;

    private String groupsJson;

    private String bracketJson;

    private String createdAt;
}