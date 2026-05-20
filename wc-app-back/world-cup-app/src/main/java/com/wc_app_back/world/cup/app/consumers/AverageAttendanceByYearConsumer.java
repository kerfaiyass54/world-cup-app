package com.wc_app_back.world.cup.app.consumers;

import com.wc_app_back.world.cup.app.entities.AvgAttendanceByYear;
import com.wc_app_back.world.cup.app.repositories.AverageAttendanceByYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AverageAttendanceByYearConsumer {

    private final AverageAttendanceByYearRepository repo;

    @KafkaListener(topics = "worldcup_avg_attendance_by_year")
    public void consume(List<Map<String, Object>> payload) {

        repo.deleteAll();

        payload.forEach(row -> {

            AvgAttendanceByYear entity = new AvgAttendanceByYear();

            entity.setYear((Integer) row.get("Year"));
            entity.setAttendanceAvg((Integer) row.get("AttendanceAvg"));

            repo.save(entity);
        });
    }
}