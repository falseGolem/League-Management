package com.github.falseGolem.LeagueManagement;

import com.github.falseGolem.LeagueManagement.models.League;
import com.github.falseGolem.LeagueManagement.models.requests.LeagueCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@LeagueIT
public class LeagueControllerIT {

    @LocalServerPort
    int port;

    @Autowired
    private TruncateDatabaseService truncateDatabaseService;

    @Before
    public void setup() {
        RestAssured.reset();
        RestAssured.port = this.port;
        RestAssured.basePath = "/";
    }

    @Test
    public void canCreateNewLeague() {
        LeagueCreateRequest request = new LeagueCreateRequest();
        request.setName("NewLeague");

        League league = given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);

        assertNotNull(league.getId());
        assertNull(league.getDescription());
        assertEquals(request.getName(), league.getName());
    }

    @Test
    public void shouldGetLeagueById() {
        LeagueCreateRequest request = new LeagueCreateRequest();
        request.setName("LeagueById");

        League league = given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);
        League returnedLeague = when().get("rest/leagues/" + league.getId()).as(League.class);

        assertEquals(league, returnedLeague);
    }

    @Test
    public void shouldAllowForTwoLeaguesWithSameName() {
        LeagueCreateRequest request = new LeagueCreateRequest();
        request.setName("SameNameLeague");
        given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);
        given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);

        List<League> filteredleagues = Arrays.stream(when().get("rest/leagues").as(League[].class))
                                             .filter(s -> s.getName().equals("SameNameLeague"))
                                             .collect(Collectors.toList());
        assertEquals(2, filteredleagues.size());
    }

    @Test
    public void shouldListAllLeagues() {
        LeagueCreateRequest request = new LeagueCreateRequest();
        request.setName("AllLeagues");
        given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);
        given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);
        given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);
        given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);
        League[] leagues = when().get("rest/leagues").as(League[].class);

        assertEquals(4, leagues.length);
    }

    @Test
    public void shouldUpdateLeague() {
        LeagueCreateRequest request = new LeagueCreateRequest();
        request.setName("UpdatedLeague");
        League league = given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);
        league.setDescription("description");
        League returnedLeague = given().contentType("application/json").body(league).when().put("rest/leagues/" + league.getId()).as(League.class);
        assertEquals(league.getDescription(), returnedLeague.getDescription());
    }

    @Test
    public void shouldDeleteLeague() {
        LeagueCreateRequest request = new LeagueCreateRequest();
        request.setName("DeletedLeague");
        League league = given().contentType("application/json").body(request).when().post("rest/leagues").as(League.class);
        assertEquals(200, when().delete("/rest/leagues/" + league.getId()).getStatusCode());
        assertEquals(0, when().get("/rest/leagues").as(League[].class).length);
    }

    @Test
    public void shouldHideServerException_getLeague() {
        Response response = when().get("/rest/leagues/aaa");
        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Looks like you're trying to send data in wrong format."));
    }

    @Test
    public void shouldHideServerException_postLeague() {
        Response response = given().contentType("application/json").body("").when().post("/rest/leagues");
        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Looks like you're trying to send empty body."));
    }

    @Test
    public void shouldHideServerException_putLeague() {
        Response response = given().contentType("application/json").body("").when().put("/rest/leagues/aaa");
        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Looks like you're trying to send empty body."));
    }

    @Test
    public void shouldHideServerException_postLeague_nullField() {
        LeagueCreateRequest leagueCreateRequest = new LeagueCreateRequest();
        leagueCreateRequest.setName(null);
        Response response = given().contentType("application/json").body(leagueCreateRequest).when().post("/rest/leagues");
        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Looks like you're trying to send null as param."));
    }

    @Test
    public void shouldHideServerException_putLeague_nullField() {
        League league = new League();
        league.setName(null);
        Response response = given().contentType("application/json").body(league).when().put("/rest/leagues/aaa");
        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Looks like you're trying to send null as param."));
    }

    @Test
    public void shouldHideServerException_deleteLeague() {
        Response response = when().delete("/rest/leagues/aaa");
        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Looks like you're trying to send data in wrong format."));
    }

    @After
    public void cleanup() throws Exception {
        truncateDatabaseService.truncate();
    }
}
