package com.exihibition.wc_exihibit.kafka;

import com.exihibition.wc_exihibit.dtos.MatchSimulationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchSimulationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void simulateMatch(
            MatchSimulationRequest request
    ) {

        kafkaTemplate.send(
                KafkaTopics.MATCH_SIMULATION_REQUEST,
                request
        );
    }
}