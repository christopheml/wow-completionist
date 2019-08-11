package com.github.christopheml.wowcompletionist.api;

/**
 * Creates the regional URLs for API endpoints.
 */
public class EndpointsResolver {

    public Endpoints forRegion(Region region) {
        return new Endpoints("https://" + region.name().toLowerCase() + ".api.blizzard.com");
    }

}
