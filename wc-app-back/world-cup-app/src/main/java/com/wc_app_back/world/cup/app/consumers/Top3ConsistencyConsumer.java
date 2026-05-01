package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.Top3ConsistencyStats;
import com.wc_app_back.world.cup.app.repositories.Top3ConsistencyStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Top3ConsistencyConsumer {

    private final Top3ConsistencyStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.top3_consistency")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            String country = (String) row.get("TEAM"); // ⚠️ check key name!
            Double score = Double.valueOf(row.get("score").toString());

            Top3ConsistencyStats entity = repo.findByCountry(country);

            if (entity == null) {
                entity = new Top3ConsistencyStats();
            }

            entity.setCountry(country);
            entity.setConsistencyScore(score);

            repo.save(entity);
        });
    }
}