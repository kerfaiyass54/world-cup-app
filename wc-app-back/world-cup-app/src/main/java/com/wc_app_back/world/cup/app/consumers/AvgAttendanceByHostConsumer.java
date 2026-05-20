package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.AvgAttendanceByHost;
import com.wc_app_back.world.cup.app.repositories.AverageAttendanceByHostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AvgAttendanceByHostConsumer {

    private final AverageAttendanceByHostRepository repo;

    @KafkaListener(topics = "worldcup_avg_attendance_by_host")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            AvgAttendanceByHost entity = new AvgAttendanceByHost();

            entity.setHost((String) row.get("Host"));
            entity.setAttendanceAvg((Integer) row.get("AttendanceAvg"));

            repo.save(entity);
        });
    }
}