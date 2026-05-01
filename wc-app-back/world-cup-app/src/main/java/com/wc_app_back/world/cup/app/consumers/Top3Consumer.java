package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.Top3Stats;
import com.wc_app_back.world.cup.app.repositories.Top3StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Top3Consumer {

    private final Top3StatsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.top3")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            String country = (String) row.get("index");
            Integer count = (Integer) row.get("count");

            Top3Stats entity = repo.findByCountry(country);

            if (entity == null) {
                entity = new Top3Stats();
            }

            entity.setCountry(country);
            entity.setAppearances(count);

            repo.save(entity);
        });
    }
}