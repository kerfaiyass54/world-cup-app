package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.AttendanceByYear;
import com.wc_app_back.world.cup.app.repositories.AttendanceByYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceByYearConsumer {

    private final AttendanceByYearRepository repo;

    @KafkaListener(topics = "worldcup_attendance_by_year")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            AttendanceByYear entity = new AttendanceByYear();

            entity.setYear((Integer) row.get("Year"));
            entity.setAttendance((Integer) row.get("Attendance"));

            repo.save(entity);
        });
    }
}