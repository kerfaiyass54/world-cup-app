package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.TeamsGrowth;
import com.wc_app_back.world.cup.app.repositories.TeamsGrowthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamsGrowthConsumer {

    private final TeamsGrowthRepository repo;

    @KafkaListener(topics = "worldcup.analytics.teams_growth")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        List<TeamsGrowth> entities = payload.stream()
                .map(row -> {

                    Integer year = ((Number) row.get("YEAR")).intValue();
                    Integer teams = ((Number) row.get("TEAMS")).intValue();

                    TeamsGrowth entity = new TeamsGrowth();
                    entity.setYear(year);
                    entity.setTeams(teams);

                    return entity;
                })
                .toList();

        repo.saveAll(entities);
    }
}