package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.RunnerupStats;
import com.wc_app_back.world.cup.app.repositories.RunnerupStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RunnerupsConsumer {

    private final RunnerupStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.runnerups")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            String country = (String) row.get("RUNNER UP");
            Integer count = ((Number) row.get("count")).intValue();

            RunnerupStats entity = repo.findByCountry(country);

            // ✅ avoid crash
            if (entity == null) {
                entity = new RunnerupStats();
                entity.setCountry(country);
            }

            entity.setRunnerupCount(count);

            repo.save(entity);
        });
    }
}