package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.ScoringByEra;
import com.wc_app_back.world.cup.app.repositories.ScoringByEraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScoringByEraConsumer {

    private final ScoringByEraRepository repo;

    @KafkaListener(topics = "worldcup.analytics.scoring_by_era")
    public void consume(List<Map<String, Object>> payload) {

        // 🔥 clear old snapshot
        repo.deleteAll();

        payload.forEach(row -> {

            String era = (String) row.get("ERA");
            Double avg = Double.valueOf(row.get("AVG GOALS PER GAME").toString());

            ScoringByEra entity = new ScoringByEra();
            entity.setEra(era);
            entity.setAvgGoalsPerGame(avg);

            repo.save(entity);
        });
    }
}