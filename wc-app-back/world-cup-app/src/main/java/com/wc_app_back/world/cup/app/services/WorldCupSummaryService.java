package com.wc_app_back.world.cup.app.services;

import com.opencsv.CSVReader;
import com.wc_app_back.world.cup.app.dtos.WorldCupSummaryDTO;
import com.wc_app_back.world.cup.app.entities.WorldCupSummaryStats;
import com.wc_app_back.world.cup.app.repositories.WorldCupSummaryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorldCupSummaryService {

    private final WorldCupSummaryRepository repo;


    // =========================
    // INIT ON STARTUP
    // =========================
    @PostConstruct
    public void init() {

        if (repo.count() > 0) {
            log.info("Summary already exists → skipping CSV load");
            return;
        }

        log.info("Loading summary from CSV...");
        loadFromCSV();
    }


    // =========================
    // LOAD FROM CSV
    // =========================
    private void loadFromCSV() {

        try {
            ClassPathResource resource =
                    new ClassPathResource("data/FIFA - World Cup Summary.csv");

            CSVReader reader = new CSVReader(
                    new InputStreamReader(resource.getInputStream())
            );

            List<String[]> rows = reader.readAll();

            // Remove header
            rows.remove(0);

            int totalEditions = rows.size();

            Set<String> champions = new HashSet<>();
            Set<String> hosts = new HashSet<>();

            double maxAvgGoals = Double.MIN_VALUE;
            int maxYear = 0;

            for (String[] row : rows) {

                int year = Integer.parseInt(row[0]);
                String host = row[1];
                String champion = row[2];
                double avgGoals = Double.parseDouble(row[9]);

                champions.add(champion);
                hosts.add(host);

                if (avgGoals > maxAvgGoals) {
                    maxAvgGoals = avgGoals;
                    maxYear = year;
                }
            }

            WorldCupSummaryStats summary = WorldCupSummaryStats.builder()
                    .totalEditions(totalEditions)
                    .totalChampions(champions.size())
                    .totalHosts(hosts.size())
                    .highestAvgGoals(maxAvgGoals)
                    .highestAvgGoalsYear(maxYear)
                    .build();

            repo.save(summary);

            log.info("Summary inserted successfully from CSV");

        } catch (Exception e) {
            log.error("Error loading CSV", e);
        }
    }


    // =========================
    // GET SUMMARY
    // =========================
    public WorldCupSummaryDTO getSummary() {

        WorldCupSummaryStats s = repo.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        return WorldCupSummaryDTO.builder()
                .totalEditions(s.getTotalEditions())
                .totalChampions(s.getTotalChampions())
                .totalHosts(s.getTotalHosts())
                .highestAvgGoals(s.getHighestAvgGoals())
                .highestAvgGoalsYear(s.getHighestAvgGoalsYear())
                .build();
    }
}