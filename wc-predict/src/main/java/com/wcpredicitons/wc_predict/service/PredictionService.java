package com.wcpredicitons.wc_predict.service;

import com.wcpredicitons.wc_predict.dto.PredictionRequest;
import com.wcpredicitons.wc_predict.model.Prediction;
import com.wcpredicitons.wc_predict.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionRepository repository;

    public Prediction savePrediction(
            PredictionRequest request
    ) {

        Prediction prediction =
                repository.findByEmail(
                        request.getEmail()
                ).orElse(new Prediction());

        prediction.setEmail(
                request.getEmail()
        );

        prediction.setChampion(
                request.getChampion()
        );

        prediction.setThirdPlace(
                request.getThirdPlace()
        );

        prediction.setGroupsJson(
                request.getGroupsJson()
        );

        prediction.setBracketJson(
                request.getBracketJson()
        );

        prediction.setCreatedAt(
                Instant.now().toString()
        );

        return repository.save(
                prediction
        );
    }

    public List<Prediction> getAll() {
        return repository.findAll();
    }

    public Prediction getByEmail(
            String email
    ) {
        return repository
                .findByEmail(email)
                .orElse(null);
    }
}