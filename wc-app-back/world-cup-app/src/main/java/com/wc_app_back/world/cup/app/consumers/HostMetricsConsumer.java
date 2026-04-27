package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.HostMetrics;
import com.wc_app_back.world.cup.app.repositories.HostMetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HostMetricsConsumer {

    private final HostMetricsRepository repo;

    @KafkaListener(topics = "worldcup.analytics.host_metrics")
    public void consume(Map<String, Object> payload) {

        payload.forEach((metric, value) -> {

            HostMetrics entity = repo.findByMetric(metric);

            entity.setMetric(metric);
            entity.setValue(Double.valueOf(value.toString()));

            repo.save(entity);
        });
    }
}