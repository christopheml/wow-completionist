package com.github.christopheml.wowcompletionist.api;


import java.util.Objects;

/**
 * Represents a unique World of Warcraft character.
 */
public class CharacterIdentity {

    private final Region region;

    private final String realm;

    private final String character;

    private CharacterIdentity(Region region, String realm, String character) {
        this.region = region;
        this.realm = realm;
        this.character = character;
    }

    public static CharacterIdentity of(Region region, String realm, String character) {
        return new CharacterIdentity(region, realm, character);
    }

    public Region getRegion() {
        return region;
    }

    public String getRealm() {
        return realm;
    }

    public String getCharacter() {
        return character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterIdentity that = (CharacterIdentity) o;
        return Objects.equals(region, that.region) &&
                Objects.equals(realm, that.realm) &&
                Objects.equals(character, that.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, realm, character);
    }

}
