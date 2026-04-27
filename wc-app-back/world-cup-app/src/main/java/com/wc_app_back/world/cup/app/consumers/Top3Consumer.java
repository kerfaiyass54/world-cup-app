package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.Top3Stats;
import com.wc_app_back.world.cup.app.repositories.Top3StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class Top3Consumer {

    private final Top3StatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.top3")
    public void consume(Map<String, Integer> payload) {

        payload.forEach((country, count) -> {

            Top3Stats entity = repo.findByCountry((country));

            entity.setCountry(country);
            entity.setAppearances(count);

            repo.save(entity);
        });
    }
}