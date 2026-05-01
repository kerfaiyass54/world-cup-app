package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.ChampionStats;
import com.wc_app_back.world.cup.app.repositories.ChampionStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChampionsConsumer {

    private final ChampionStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.champions")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            String country = (String) row.get("CHAMPION");
            Integer wins = ((Number) row.get("count")).intValue();

            ChampionStats entity = repo.findByCountry(country);

            if (entity == null) {
                entity = new ChampionStats();
                entity.setCountry(country);
            }

            entity.setWins(wins);

            repo.save(entity);
        });
    }
}