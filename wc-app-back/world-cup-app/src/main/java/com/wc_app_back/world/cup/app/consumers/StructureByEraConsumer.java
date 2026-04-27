package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.StructureByEra;
import com.wc_app_back.world.cup.app.repositories.StructureByEraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StructureByEraConsumer {

    private final StructureByEraRepository repo;

    @KafkaListener(topics = "worldcup.analytics.structure_by_era")
    public void consume(Map<String, Map<String, Double>> payload) {

        payload.forEach((era, values) -> {

            StructureByEra entity = repo.findByEra(era);

            entity.setEra(era);
            entity.setAvgTeams(values.get("TEAMS"));
            entity.setAvgMatches(values.get("MATCHES PLAYED"));
            entity.setAvgGoals(values.get("GOALS SCORED"));

            repo.save(entity);
        });
    }
}