package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.AttendanceByHost;
import com.wc_app_back.world.cup.app.repositories.AttendanceByHostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceByHostConsumer {

    private final AttendanceByHostRepository repo;

    @KafkaListener(topics = "worldcup_attendance_by_host")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            AttendanceByHost entity = new AttendanceByHost();

            entity.setHost((String) row.get("Host"));
            entity.setAttendance((Integer) row.get("Attendance"));

            repo.save(entity);
        });
    }
}