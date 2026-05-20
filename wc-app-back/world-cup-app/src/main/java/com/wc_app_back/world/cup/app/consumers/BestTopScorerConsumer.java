package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.BestTopScorer;
import com.wc_app_back.world.cup.app.repositories.BestTopScorerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BestTopScorerConsumer {

    private final BestTopScorerRepository repo;

    @KafkaListener(topics = "worldcup_best_top_scorer")
    public void consume(Map<String, Object> payload) {

        repo.deleteAll();

        BestTopScorer entity = new BestTopScorer();

        entity.setYear((Integer) payload.get("Year"));
        entity.setPlayer((String) payload.get("Player"));
        entity.setGoals((Integer) payload.get("Goals"));

        repo.save(entity);
    }
}