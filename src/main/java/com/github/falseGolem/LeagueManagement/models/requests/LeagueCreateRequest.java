package com.github.falseGolem.LeagueManagement.models.requests;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class LeagueCreateRequest {

    @Size(min = 1, max = 64)
    @NotBlank
    private String name;

    @Size(max = 255)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
