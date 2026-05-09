package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.ThirdplaceStats;
import com.wc_app_back.world.cup.app.repositories.ThirdplaceStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ThirdplaceConsumer {

    private final ThirdplaceStatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.thirdplace")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            String country = (String) row.get("THIRD PLACE");
            Integer count = (Integer) row.get("count");

            ThirdplaceStats entity = new ThirdplaceStats();
            entity.setCountry(country);
            entity.setThirdplaceCount(count);

            repo.save(entity);
        });
    }
}