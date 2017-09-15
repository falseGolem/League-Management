package com.github.falseGolem.LeagueManagement;

import com.github.falseGolem.LeagueManagement.controllers.LeaguesController;
import com.github.falseGolem.LeagueManagement.controllers.exceptions.LeaguesNpeException;
import com.github.falseGolem.LeagueManagement.dummy.DummyLeaguesService;
import com.github.falseGolem.LeagueManagement.models.League;
import com.github.falseGolem.LeagueManagement.models.requests.LeagueCreateRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class LeagueControllerTest {

    private LeaguesController leaguesController;

    @Before
    public void setup() {
        this.leaguesController = new LeaguesController(new DummyLeaguesService());
    }

    @Test(expected = LeaguesNpeException.class)
    public void shouldHandleNegativeScenariosOnGet_null() throws LeaguesNpeException {
        leaguesController.getLeague(null);
    }

    @Test(expected = LeaguesNpeException.class)
    public void shouldHandleNegativeScenariosOnPut_null() {
        leaguesController.putLeague(null, UUID.randomUUID());
    }

    @Test(expected = LeaguesNpeException.class)
    public void shouldHandleNegativeScenariosOnPut_nullField() {
        League league = new League();
        league.setName(null);
        leaguesController.putLeague(league, UUID.randomUUID());
    }

    @Test(expected = LeaguesNpeException.class)
    public void shouldHandleNegativeScenariosOnDelete_null() {
        leaguesController.deleteLeague(null);
    }

    @Test(expected = LeaguesNpeException.class)
    public void shouldHandleNegativeScenariosOnPost_null() {
        leaguesController.postLeague(null);
    }

    @Test(expected = LeaguesNpeException.class)
    public void shouldHandleNegativeScenariosOnPost_nullField() {
        LeagueCreateRequest leagueCreateRequest = new LeagueCreateRequest();
        leagueCreateRequest.setName(null);
        leaguesController.postLeague(leagueCreateRequest);
    }
}
