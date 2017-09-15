package com.github.falseGolem.LeagueManagement.services;

import com.github.falseGolem.LeagueManagement.controllers.exceptions.LeaguesNpeException;
import com.github.falseGolem.LeagueManagement.models.League;
import com.github.falseGolem.LeagueManagement.models.requests.LeagueCreateRequest;
import com.github.falseGolem.LeagueManagement.repositories.LeaguesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        if (leagueCreateRequest == null || StringUtils.isEmpty(leagueCreateRequest.getName())) {
            throw new LeaguesNpeException("League object cannot be null.");
        }
        League league = new League();
        league.setName(leagueCreateRequest.getName());
        league.setDescription(leagueCreateRequest.getDescription());
        return leaguesRepository.saveAndFlush(league);
    }

    public List<League> getAll() {
        return leaguesRepository.findAll();
    }

    public League get(final UUID uuid) throws LeaguesNpeException {
        if (uuid == null) {
            throw new LeaguesNpeException("League UUID cannot be null.");
        }
        return leaguesRepository.findOne(uuid);
    }

    public void delete(final UUID uuid) throws LeaguesNpeException {
        if (uuid == null) {
            throw new LeaguesNpeException("League UUID cannot be null.");
        }
        leaguesRepository.delete(uuid);
    }

    public League update(final League updatedLeague) throws LeaguesNpeException {
        if (updatedLeague == null || StringUtils.isEmpty(updatedLeague.getName())) {
            throw new LeaguesNpeException("League object cannot be null.");
        }
        else if (!leaguesRepository.exists(updatedLeague.getId())) {
            throw new LeaguesNpeException("No league with such UUID: " + updatedLeague.getId());
        }
        return leaguesRepository.saveAndFlush(updatedLeague);
    }
}
