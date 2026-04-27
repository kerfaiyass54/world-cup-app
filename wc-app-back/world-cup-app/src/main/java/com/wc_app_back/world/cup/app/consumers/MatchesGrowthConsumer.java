package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.MatchesGrowth;
import com.wc_app_back.world.cup.app.repositories.MatchesGrowthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatchesGrowthConsumer {

    private final MatchesGrowthRepository repo;

    @KafkaListener(topics = "worldcup.analytics.matches_growth")
    public void consume(List<Map<String, Object>> payload) {

        payload.forEach(row -> {

            Integer year = (Integer) row.get("YEAR");
            Integer matches = (Integer) row.get("MATCHES PLAYED");

            MatchesGrowth entity = repo.findByYear(year);

            entity.setYear(year);
            entity.setMatches(matches);

            repo.save(entity);
        });
    }
}