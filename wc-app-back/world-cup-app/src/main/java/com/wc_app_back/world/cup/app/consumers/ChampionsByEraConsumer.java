package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.ChampionEraStats;
import com.wc_app_back.world.cup.app.repositories.ChampionEraStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChampionsByEraConsumer {

    private final ChampionEraStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.champions_by_era")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            String era = (String) row.get("ERA");
            String country = (String) row.get("CHAMPION");

            // 👇 VERY IMPORTANT (your column name is "0")
            Integer wins = ((Number) row.get("0")).intValue();

            ChampionEraStats entity =
                    repo.findByEraAndCountry(era, country);

            if (entity == null) {
                entity = new ChampionEraStats();
            }

            entity.setEra(era);
            entity.setCountry(country);
            entity.setWins(wins);

            repo.save(entity);
        });
    }
}