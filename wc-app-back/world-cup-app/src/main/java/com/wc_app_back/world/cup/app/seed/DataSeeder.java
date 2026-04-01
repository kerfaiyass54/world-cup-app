package com.wc_app_back.world.cup.app.seed;


import com.wc_app_back.world.cup.app.entities.Cup;
import com.wc_app_back.world.cup.app.entities.Nation;
import com.wc_app_back.world.cup.app.entities.Participation;
import com.wc_app_back.world.cup.app.repositories.CupRepository;
import com.wc_app_back.world.cup.app.repositories.NationRepository;
import com.wc_app_back.world.cup.app.repositories.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final CupRepository          cupRepository;
    private final NationRepository       nationRepository;
    private final ParticipationRepository participationRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        seedCups();
        seedNations();
        seedParticipations();
    }

    // ── Cups ─────────────────────────────────────────────────────────────────

    private void seedCups() throws Exception {
        if (cupRepository.count() > 0) {
            log.info("[Seeder] Cups already seeded – skipping.");
            return;
        }

        List<Cup> cups = new ArrayList<>();
        try (BufferedReader reader = openCsv("cups.csv")) {
            String line;
            boolean header = true;
            while ((line = reader.readLine()) != null) {
                if (header) { header = false; continue; }
                String[] cols = line.split(",", -1);
                if (cols.length < 2) continue;

                Cup cup = new Cup();
                cup.setYear(Integer.parseInt(cols[0].trim()));
                cup.setHost(cols[1].trim());
                cup.setWinner(cols.length > 2 && !cols[2].isBlank() ? cols[2].trim() : null);
                cups.add(cup);
            }
        }
        cupRepository.saveAll(cups);
        log.info("[Seeder] {} cups seeded.", cups.size());
    }

    // ── Nations ───────────────────────────────────────────────────────────────

    private void seedNations() throws Exception {
        if (nationRepository.count() > 0) {
            log.info("[Seeder] Nations already seeded – skipping.");
            return;
        }

        List<Nation> nations = new ArrayList<>();
        try (BufferedReader reader = openCsv("nations.csv")) {
            String line;
            boolean header = true;
            while ((line = reader.readLine()) != null) {
                if (header) { header = false; continue; }
                String[] cols = line.split(",", -1);
                if (cols.length < 2) continue;

                Nation nation = new Nation();
                nation.setName(cols[0].trim());
                nation.setContinent(cols[1].trim());
                nations.add(nation);
            }
        }
        nationRepository.saveAll(nations);
        log.info("[Seeder] {} nations seeded.", nations.size());
    }

    // ── Participations ────────────────────────────────────────────────────────

    private void seedParticipations() throws Exception {
        if (participationRepository.count() > 0) {
            log.info("[Seeder] Participations already seeded – skipping.");
            return;
        }

        Map<String, Nation> nationsByName = nationRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Nation::getName, Function.identity()));

        Map<Integer, Cup> cupsByYear = cupRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Cup::getYear, Function.identity()));

        List<Participation>  participations = new ArrayList<>();
        List<String>         skipped        = new ArrayList<>();

        try (BufferedReader reader = openCsv("participations.csv")) {
            String line;
            boolean header = true;
            while ((line = reader.readLine()) != null) {
                if (header) { header = false; continue; }
                String[] cols = line.split(",", -1);
                if (cols.length < 2) continue;

                String  nationName = cols[0].trim();
                Integer cupYear    = Integer.parseInt(cols[1].trim());

                Nation nation = nationsByName.get(nationName);
                Cup    cup    = cupsByYear.get(cupYear);

                if (nation == null || cup == null) {
                    skipped.add(nationName + " / " + cupYear);
                    continue;
                }

                Participation p = new Participation();
                p.setNation(nation);
                p.setCup(cup);
                participations.add(p);
            }
        }

        participationRepository.saveAll(participations);
        nationRepository.saveAll(new ArrayList<>(nationsByName.values()));

        if (!skipped.isEmpty()) {
            log.warn("[Seeder] {} participation(s) skipped (unresolved): {}", skipped.size(), skipped);
        }
        log.info("[Seeder] {} participations seeded.", participations.size());
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private BufferedReader openCsv(String filename) throws Exception {
        return new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource(filename).getInputStream(),
                        StandardCharsets.UTF_8
                )
        );
    }
}