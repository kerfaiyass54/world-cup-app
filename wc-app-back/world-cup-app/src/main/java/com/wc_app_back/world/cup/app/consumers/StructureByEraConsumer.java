package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.StructureByEra;
import com.wc_app_back.world.cup.app.repositories.StructureByEraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StructureByEraConsumer {

    private final StructureByEraRepository repo;

    @KafkaListener(topics = "worldcup.analytics.structure_by_era")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            String era = (String) row.get("ERA");

            Double teams = Double.valueOf(row.get("TEAMS").toString());
            Double matches = Double.valueOf(row.get("MATCHES PLAYED").toString());
            Double goals = Double.valueOf(row.get("GOALS SCORED").toString());

            StructureByEra entity = new StructureByEra();
            entity.setEra(era);
            entity.setAvgTeams(teams);
            entity.setAvgMatches(matches);
            entity.setAvgGoals(goals);

            repo.save(entity);
        });
    }
}