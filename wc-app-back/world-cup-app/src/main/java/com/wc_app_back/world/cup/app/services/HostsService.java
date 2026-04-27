package com.wc_app_back.world.cup.app.services;


import com.wc_app_back.world.cup.app.dtos.HostMetricsDTO;
import com.wc_app_back.world.cup.app.entities.HostMetrics;
import com.wc_app_back.world.cup.app.repositories.HostMetricsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HostsService {

    private final HostMetricsRepository repo;

    private static final int TOTAL_TOURNAMENTS = 22; // fixed dataset


    // =========================
    // MAIN METHOD
    // =========================
    public HostMetricsDTO getHostMetrics() {

        int top3 = getMetricAsInt("host_top3_count");
        int finalCount = getMetricAsInt("host_final_count");
        int champion = getMetricAsInt("host_champion_count");
        double ratio = getMetricAsDouble("host_top3_ratio");

        int notTop3 = TOTAL_TOURNAMENTS - top3;

        return HostMetricsDTO.builder()
                .hostTop3Count(top3)
                .hostFinalCount(finalCount)
                .hostChampionCount(champion)
                .hostNotTop3Count(notTop3)
                .hostTop3Ratio(ratio)
                .build();
    }


    // =========================
    // HELPERS
    // =========================
    private int getMetricAsInt(String metric) {

        HostMetrics entity = repo.findByMetric(metric);

        if (entity == null) return 0;

        return entity.getValue().intValue();
    }

    private double getMetricAsDouble(String metric) {

        HostMetrics entity = repo.findByMetric(metric);

        if (entity == null) return 0.0;

        return entity.getValue();
    }
}