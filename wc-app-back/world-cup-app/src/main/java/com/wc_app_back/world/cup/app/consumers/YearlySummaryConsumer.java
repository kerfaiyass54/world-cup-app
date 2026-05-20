package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.YearlySummary;
import com.wc_app_back.world.cup.app.repositories.WorldCupYearlySummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class YearlySummaryConsumer {

    private final WorldCupYearlySummaryRepository repo;

    @KafkaListener(topics = "worldcup_yearly_summary")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            YearlySummary entity = new YearlySummary();

            entity.setYear((Integer) row.get("Year"));
            entity.setHost((String) row.get("Host"));
            entity.setChampion((String) row.get("Champion"));
            entity.setRunnerUp((String) row.get("Runner-Up"));
            entity.setMatches((Integer) row.get("Matches"));
            entity.setTopScorerPlayer((String) row.get("TopScorerPlayer"));
            entity.setTopScorerGoals((Integer) row.get("TopScorerGoals"));

            repo.save(entity);
        });
    }
}