package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.HostTop3ByCountry;
import com.wc_app_back.world.cup.app.repositories.HostTop3ByCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HostTop3ByCountryConsumer {

    private final HostTop3ByCountryRepository repo;

    @KafkaListener(topics = "worldcup.analytics.host_top3_by_country")
    public void consume(Map<String, Integer> payload) {

        payload.forEach((country, count) -> {

            HostTop3ByCountry entity = repo.findByCountry(country);

            entity.setCountry(country);
            entity.setTop3Count(count);

            repo.save(entity);
        });
    }
}