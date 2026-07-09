package com.exihibition.wc_exihibit.repositories;

import com.exihibition.wc_exihibit.entities.Tournament;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TournamentRepository
        extends MongoRepository<Tournament, String> {

    List<Tournament> findByEmail(
            String email
    );
}