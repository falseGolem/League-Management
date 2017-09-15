package com.github.falseGolem.LeagueManagement.dummy;

import com.github.falseGolem.LeagueManagement.models.League;
import com.github.falseGolem.LeagueManagement.services.LeaguesService;

import java.util.UUID;

public class DummyLeaguesService extends LeaguesService {

    public DummyLeaguesService() {
        super(null);
    }

    @Override
    public League get(UUID uuid) {
        if (uuid == null) {
            return super.get(uuid);
        }
        return new League();
    }
}
