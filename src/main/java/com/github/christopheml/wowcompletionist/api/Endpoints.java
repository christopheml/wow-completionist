package com.github.christopheml.wowcompletionist.api;

import com.github.christopheml.wowcompletionist.Region;

/**
 * Provides URLs for Blizzard API endpoints.
 */
public class Endpoints {

    public static final Endpoints EUROPE = new Endpoints(Region.EU);

    private final String baseUrl;

    private Endpoints(Region region) {
        baseUrl = "https://" + region.name().toLowerCase() + ".api.blizzard.com";
    }

    public String battlePets(String realm, String character) {
        return baseUrl + "/wow/character/" + realm + "/" + character + "?fields=pets";
    }

}
