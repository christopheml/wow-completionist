package com.github.christopheml.wowcompletionist.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents World of Warcraft Character API data.
 */
public class Character {

    @JsonProperty
    private Pets pets;

    public Pets getPets() {
        return pets;
    }

}
