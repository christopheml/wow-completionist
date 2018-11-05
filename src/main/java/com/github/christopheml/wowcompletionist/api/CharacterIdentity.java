package com.github.christopheml.wowcompletionist.api;


/**
 * Represents a unique World of Warcraft character.
 */
public class CharacterIdentity {

    private final String region;

    private final String realm;

    private final String character;

    private CharacterIdentity(String region, String realm, String character) {
        this.region = region;
        this.realm = realm;
        this.character = character;
    }

    public static CharacterIdentity of(String region, String realm, String character) {
        return new CharacterIdentity(region, realm, character);
    }

    public String getRegion() {
        return region;
    }

    public String getRealm() {
        return realm;
    }

    public String getCharacter() {
        return character;
    }

}
