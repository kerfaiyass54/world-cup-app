package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.ThirdplaceStats;
import com.wc_app_back.world.cup.app.repositories.ThirdplaceStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ThirdplaceConsumer {

    private final ThirdplaceStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.thirdplace")
    public void consume(Map<String, Integer> payload) {

        payload.forEach((country, count) -> {

            ThirdplaceStats entity = repo.findByCountry(country)
                    .orElse(new ThirdplaceStats());

            entity.setCountry(country);
            entity.setThirdplaceCount(count);

            repo.save(entity);
        });
    }
}