package com.wcpredicitons.wc_predict.controller;

import com.wcpredicitons.wc_predict.dto.PredictionRequest;
import com.wcpredicitons.wc_predict.model.Prediction;
import com.wcpredicitons.wc_predict.service.PredictionService;
import lombok.RequiredArgsConstructor;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PredictionGraphQLController {

    private final PredictionService service;

    @MutationMapping
    public Prediction savePrediction(
            @Argument PredictionRequest request
    ) {
        return service.savePrediction(request);
    }

    @QueryMapping
    public List<Prediction> predictions() {
        return service.getAll();
    }

    @QueryMapping
    public Prediction predictionByEmail(
            @Argument String email
    ) {
        return service.getByEmail(email);
    }
}