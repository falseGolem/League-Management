package com.github.falseGolem.LeagueManagement.repositories;

import com.github.falseGolem.LeagueManagement.models.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaguesRepository extends JpaRepository<League, UUID> {
}
