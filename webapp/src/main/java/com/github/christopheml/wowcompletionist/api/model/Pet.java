package com.github.christopheml.wowcompletionist.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a World of Warcraft battle or companion pet.
 */
public class Pet {

    @JsonProperty
    private int creatureId;

    @JsonProperty
    private int qualityId;

    @JsonProperty
    private String name;

    @JsonProperty
    private String icon;

    @JsonProperty
    private boolean canBattle;

    public int getCreatureId() {
        return creatureId;
    }

    public int getQualityId() {
        return qualityId;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public boolean canBattle() {
        return canBattle;
    }

}
