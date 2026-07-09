package com.exihibition.wc_exihibit.services;

import com.exihibition.wc_exihibit.entities.Group;
import org.springframework.stereotype.Service;

@Service
public class GroupFinishedService {

    public boolean isFinished(Group group) {

        return group.getMatches()
                .stream()
                .allMatch(match ->
                        match.isPlayed()
                );
    }
}