package com.wcpredicitons.wc_predict.repository;

import com.wcpredicitons.wc_predict.model.Prediction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PredictionRepository
        extends MongoRepository<Prediction, String> {

    Optional<Prediction> findByEmail(
            String email
    );
}