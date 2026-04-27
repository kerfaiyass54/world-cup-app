package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.RunnerupStats;
import com.wc_app_back.world.cup.app.repositories.RunnerupStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RunnerupsConsumer {

    private final RunnerupStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.runnerups")
    public void consume(Map<String, Integer> payload) {

        payload.forEach((country, count) -> {

            RunnerupStats entity = repo.findByCountry(country);

            entity.setCountry(country);
            entity.setRunnerupCount(count);

            repo.save(entity);
        });
    }
}
