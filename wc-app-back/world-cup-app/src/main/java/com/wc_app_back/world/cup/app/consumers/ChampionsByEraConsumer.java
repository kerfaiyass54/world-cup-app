package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.ChampionEraStats;
import com.wc_app_back.world.cup.app.repositories.ChampionEraStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChampionsByEraConsumer {

    private final ChampionEraStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.champions_by_era")
    public void consume(Map<String, Map<String, Integer>> payload) {

        payload.forEach((era, countries) -> {

            countries.forEach((country, wins) -> {

                ChampionEraStats entity = repo
                        .findByEraAndCountry(era, country);

                entity.setEra(era);
                entity.setCountry(country);
                entity.setWins(wins);

                repo.save(entity);
            });
        });
    }
}