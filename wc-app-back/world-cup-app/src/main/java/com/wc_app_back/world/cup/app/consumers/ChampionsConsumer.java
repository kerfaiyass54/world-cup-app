package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.ChampionStats;
import com.wc_app_back.world.cup.app.repositories.ChampionStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChampionsConsumer {

    private final ChampionStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.champions")
    public void consume(Map<String, Integer> payload) {

        payload.forEach((country, wins) -> {

            ChampionStats entity = repo.findByCountry(country);

            entity.setCountry(country);
            entity.setWins(wins);

            repo.save(entity);
        });
    }
}
