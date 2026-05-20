package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.TopScorerGoals;
import com.wc_app_back.world.cup.app.repositories.TopScorerGoalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TopScorerGoalsConsumer {

    private final TopScorerGoalsRepository repo;

    @KafkaListener(topics = "worldcup_top_scorer_goals")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            TopScorerGoals entity = new TopScorerGoals();

            entity.setYear((Integer) row.get("Year"));
            entity.setTopScorerPlayer((String) row.get("TopScorerPlayer"));
            entity.setTopScorerGoals((Integer) row.get("TopScorerGoals"));

            repo.save(entity);
        });
    }
}