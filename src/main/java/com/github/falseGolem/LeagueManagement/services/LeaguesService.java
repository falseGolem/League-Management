package com.github.falseGolem.LeagueManagement.services;

import com.github.falseGolem.LeagueManagement.models.League;
import com.github.falseGolem.LeagueManagement.models.requests.LeagueCreateRequest;
import com.github.falseGolem.LeagueManagement.repositories.LeaguesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LeaguesService {

    private final LeaguesRepository leaguesRepository;

    @Autowired
    public LeaguesService(LeaguesRepository leaguesRepository) {
        this.leaguesRepository = leaguesRepository;
    }

    public League create(final LeagueCreateRequest leagueCreateRequest) {
        League league = new League();
        league.setName(leagueCreateRequest.getName());
        league.setDescription(leagueCreateRequest.getDescription());
        return leaguesRepository.saveAndFlush(league);
    }

    public List<League> getAll() {
        return leaguesRepository.findAll();
    }

    public League get(final UUID uuid) {
        return leaguesRepository.findOne(uuid);
    }

    public void delete(final UUID uuid) {
        leaguesRepository.delete(uuid);
    }

    public League update(final League league) {
        return leaguesRepository.saveAndFlush(league);
    }
}
