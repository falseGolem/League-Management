package com.github.falseGolem.LeagueManagement.controllers;

import com.github.falseGolem.LeagueManagement.models.League;
import com.github.falseGolem.LeagueManagement.models.requests.LeagueCreateRequest;
import com.github.falseGolem.LeagueManagement.services.LeaguesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "rest/leagues", produces = "application/json")
public class LeaguesController {

    private LeaguesService leaguesService;

    @Autowired
    public LeaguesController(LeaguesService leaguesService) {
        this.leaguesService = leaguesService;
    }

    @GetMapping
    public List<League> getLeagues() {
        return leaguesService.getAll();
    }

    @GetMapping(value = "/{uuid}")
    public League getLeague(@PathVariable UUID uuid) {
        return leaguesService.get(uuid);
    }

    @PostMapping
    public League postLeague(@RequestBody LeagueCreateRequest leagueCreateRequest) {
        return leaguesService.create(leagueCreateRequest);
    }

    @PutMapping(value = "/{uuid}")
    public League putLeague(@RequestBody League league) {
        return leaguesService.update(league);
    }

    @DeleteMapping(value = "/{uuid}")
    public void deleteLeague(@PathVariable UUID uuid) {
        leaguesService.delete(uuid);
    }
}
