package com.github.christopheml.wowcompletionist.api;

/**
 * Represents API endpoints for a given server.
 */
public class Endpoints {

    private final String baseUrl;

    public Endpoints(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String characterBattlePets(String realm, String character) {
        return baseUrl + "/wow/character/" + realm + "/" + character + "?fields=pets";
    }

}
