package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.HostMetrics;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostMetricsRepository extends JpaRepository<HostMetrics, Long> {
    HostMetrics findByMetric(String metric);
}