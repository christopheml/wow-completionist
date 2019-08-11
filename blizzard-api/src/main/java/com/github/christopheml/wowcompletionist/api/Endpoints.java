package com.github.christopheml.wowcompletionist.api;

import java.util.EnumMap;
import java.util.Map;

/**
 * Provides URLs for Blizzard API endpoints.
 */
public class Endpoints {

    private static final Map<Region, Endpoints> regionalEndpoints = new EnumMap<>(Region.class);

    static {
        for (Region region : Region.values()) {
            regionalEndpoints.put(region, new Endpoints(region));
        }
    }

    private final String baseUrl;

    private Endpoints(Region region) {
        baseUrl = "https://" + region.name().toLowerCase() + ".api.blizzard.com";
    }

    public String characterBattlePets(String realm, String character) {
        return baseUrl + "/wow/character/" + realm + "/" + character + "?fields=pets";
    }

    public static Endpoints forRegion(Region region) {
        return regionalEndpoints.get(region);
    }

}
