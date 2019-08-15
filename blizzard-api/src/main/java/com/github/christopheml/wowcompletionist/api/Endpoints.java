package com.github.christopheml.wowcompletionist.api;

/**
 * Represents API endpoints for a given server.
 */
public class Endpoints {

    private final String baseUrl;

    public Endpoints(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Battle pets information from Character Profile API.
     */
    public String characterBattlePets(String realm, String character) {
        return baseUrl + "/wow/character/" + realm + "/" + character + "?fields=pets";
    }

    /**
     * Pet master list from Master List.
     */
    public String allPets() {
        return baseUrl + "/wow/pet/";
    }

}
