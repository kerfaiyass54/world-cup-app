package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.ScoringCorrStats;
import com.wc_app_back.world.cup.app.repositories.ScoringCorrStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScoringCorrConsumer {

    private final ScoringCorrStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.scoring_corr")
    public void consume(Map<String, Double> payload) {

        payload.forEach((metric, value) -> {

            ScoringCorrStats entity = repo.findByMetric(metric);

            entity.setMetric(metric);
            entity.setValue(value);

            repo.save(entity);
        });
    }
}