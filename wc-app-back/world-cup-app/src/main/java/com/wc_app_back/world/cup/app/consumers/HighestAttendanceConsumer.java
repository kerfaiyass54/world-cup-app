package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.HighestAttendance;
import com.wc_app_back.world.cup.app.repositories.HighestAttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HighestAttendanceConsumer {

    private final HighestAttendanceRepository repo;

    @KafkaListener(topics = "worldcup_highest_attendance")
    public void consume(Map<String, Object> payload) {

        repo.deleteAll();

        HighestAttendance entity = new HighestAttendance();

        entity.setYear((Integer) payload.get("Year"));
        entity.setHost((String) payload.get("Host"));
        entity.setAttendance((Integer) payload.get("Attendance"));

        repo.save(entity);
    }
}