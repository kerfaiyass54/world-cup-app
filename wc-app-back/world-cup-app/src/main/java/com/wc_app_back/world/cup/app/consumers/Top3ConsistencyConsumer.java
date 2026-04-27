package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.Top3ConsistencyStats;
import com.wc_app_back.world.cup.app.repositories.Top3ConsistencyStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class Top3ConsistencyConsumer {

    private final Top3ConsistencyStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.top3_consistency")
    public void consume(Map<String, Double> payload) {

        payload.forEach((country, score) -> {

            Top3ConsistencyStats entity = repo.findByCountry(country);

            entity.setCountry(country);
            entity.setConsistencyScore(score);

            repo.save(entity);
        });
    }
}