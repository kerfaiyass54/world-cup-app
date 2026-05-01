package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.RunnerupWithoutTitles;
import com.wc_app_back.world.cup.app.repositories.RunnerupWithoutTitlesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RunnerupsWithoutTitlesConsumer {

    private final RunnerupWithoutTitlesRepository repo;

    @KafkaListener(topics = "worldcup.analytics.runnerups_without_titles")
    public void consume(List<String> payload) {

        // 🔥 Clear old data (IMPORTANT)
        repo.deleteAll();

        payload.forEach(country -> {
            RunnerupWithoutTitles entity = new RunnerupWithoutTitles();
            entity.setCountry(country);
            repo.save(entity);
        });
    }
}
