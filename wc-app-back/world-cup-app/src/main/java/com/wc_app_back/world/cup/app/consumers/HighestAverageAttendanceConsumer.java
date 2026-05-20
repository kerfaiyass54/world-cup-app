package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.HighestAvgAttendance;
import com.wc_app_back.world.cup.app.repositories.HighestAverageAttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HighestAverageAttendanceConsumer {

    private final HighestAverageAttendanceRepository repo;

    @KafkaListener(topics = "worldcup_highest_avg_attendance")
    public void consume(Map<String, Object> payload) {

        repo.deleteAll();

        HighestAvgAttendance entity = new HighestAvgAttendance();

        entity.setYear((Integer) payload.get("Year"));
        entity.setHost((String) payload.get("Host"));
        entity.setAttendanceAvg((Integer) payload.get("AttendanceAvg"));

        repo.save(entity);
    }
}