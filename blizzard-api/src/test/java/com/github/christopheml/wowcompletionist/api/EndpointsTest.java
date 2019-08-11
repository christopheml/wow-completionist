package com.github.christopheml.wowcompletionist.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EndpointsTest {

    @Test
    void battlePetsEndpoint() {
        Assertions.assertThat(Endpoints.forRegion(Region.EU).characterBattlePets("Hyjal", "Giantstone"))
                .isEqualTo("https://eu.api.blizzard.com/wow/character/Hyjal/Giantstone?fields=pets");

        Assertions.assertThat(Endpoints.forRegion(Region.US).characterBattlePets("Stormrage", "Ryken"))
                .isEqualTo("https://us.api.blizzard.com/wow/character/Stormrage/Ryken?fields=pets");
    }

}
