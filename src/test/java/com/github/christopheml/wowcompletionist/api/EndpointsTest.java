package com.github.christopheml.wowcompletionist.api;

import com.github.christopheml.wowcompletionist.Region;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EndpointsTest {

    @Test
    void battlePetsEndpoint() {
        assertThat(Endpoints.forRegion(Region.EU).battlePets("Hyjal", "Giantstone"))
                .isEqualTo("https://eu.api.blizzard.com/wow/character/Hyjal/Giantstone?fields=pets");

        assertThat(Endpoints.forRegion(Region.US).battlePets("Stormrage", "Ryken"))
                .isEqualTo("https://us.api.blizzard.com/wow/character/Stormrage/Ryken?fields=pets");
    }

}
