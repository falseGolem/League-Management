package com.github.falseGolem.LeagueManagement;

import com.github.falseGolem.LeagueManagement.controllers.LeaguesController;
import com.github.falseGolem.LeagueManagement.controllers.exceptions.LeaguesNpeException;
import com.github.falseGolem.LeagueManagement.dummy.DummyLeaguesService;
import com.github.falseGolem.LeagueManagement.models.League;
import com.github.falseGolem.LeagueManagement.models.requests.LeagueCreateRequest;
import org.junit.Before;
import org.junit.Test;

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
        leaguesController.putLeague(null);
    }

    @Test(expected = LeaguesNpeException.class)
    public void shouldHandleNegativeScenariosOnPut_nullField() {
        League league = new League();
        league.setName(null);
        leaguesController.putLeague(league);
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
