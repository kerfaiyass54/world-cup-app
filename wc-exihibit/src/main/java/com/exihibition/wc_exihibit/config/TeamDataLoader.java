package com.exihibition.wc_exihibit.config;

import com.exihibition.wc_exihibit.entities.Team;
import com.exihibition.wc_exihibit.enums.Continent;
import com.exihibition.wc_exihibit.enums.TeamMoral;
import com.exihibition.wc_exihibit.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TeamDataLoader
        implements CommandLineRunner {

    private final TeamRepository
            teamRepository;

    @Override
    public void run(
            String... args
    ) throws Exception {

        ClassPathResource resource =
                new ClassPathResource(
                        "teams.csv"
                );

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                resource.getInputStream(),
                                StandardCharsets.UTF_8
                        )
                );

        String line;

        boolean firstLine = true;

        while (
                (line = reader.readLine())
                        != null
        ) {

            if (firstLine) {

                firstLine = false;

                continue;
            }

            String[] values =
                    line.split(",");

            if (values.length < 4) {
                continue;
            }

            String name =
                    values[0].trim();

            Integer ability =
                    Integer.parseInt(
                            values[1].trim()
                    );

            TeamMoral moral =
                    TeamMoral.valueOf(
                            values[2].trim()
                    );

            Continent continent =
                    Continent.valueOf(
                            values[3].trim()
                    );

            Optional<Team> existing =
                    teamRepository
                            .findByName(
                                    name
                            );

            Team team =
                    existing.orElse(
                            new Team()
                    );

            team.setName(name);

            team.setAbility(
                    ability
            );

            team.setMoral(
                    moral
            );

            team.setContinent(
                    continent
            );

            teamRepository.save(
                    team
            );
        }

        reader.close();

        System.out.println(
                "Teams synchronized successfully."
        );
    }
}