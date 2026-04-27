package com.wc_app_back.world.cup.app.repositories;

import com.wc_app_back.world.cup.app.entities.HostTop3ByCountry;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostTop3ByCountryRepository extends JpaRepository<HostTop3ByCountry, Long> {
    HostTop3ByCountry findByCountry(String country);
}