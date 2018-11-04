package com.github.christopheml.wowcompletionist.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Pets {

    @JsonProperty("numCollected")
    private int collectedCount;

    @JsonProperty("numNotCollected")
    private int uncollectedCount;

    @JsonProperty
    private List<Pet> collected;

    /**
     * Returns how many pets the player has collected, including duplicates.
     */
    public long getCollectedCount() {
        return collectedCount;
    }

    /**
     * Returns how many pets the player has not yet collected.
     */
    public long getUncollectedCount() {
        return uncollectedCount;
    }

    /**
     * Returns how many unique pets the player has collected.
     */
    public long getUniqueCollectedCount() {
        return collected.stream().map(Pet::getCreatureId).distinct().count();
    }

    public List<Pet> getCollected() {
        return collected;
    }

}
