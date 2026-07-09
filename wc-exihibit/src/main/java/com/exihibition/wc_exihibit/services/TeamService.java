package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.entities.Team;
import com.exihibition.wc_exihibit.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getAll() {

        return teamRepository.findAll();
    }

    public Team getById(
            String id
    ) {

        return teamRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Team not found"
                        )
                );
    }
}