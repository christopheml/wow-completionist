package com.github.christopheml.wowcompletionist.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EndpointsResolverTest {

    @Test
    void battlePetsEndpoint() {
        EndpointsResolver resolver = new EndpointsResolver();

        assertThat(resolver.forRegion(Region.EU).characterBattlePets("Hyjal", "Giantstone"))
                .isEqualTo("https://eu.api.blizzard.com/wow/character/Hyjal/Giantstone?fields=pets");

        assertThat(resolver.forRegion(Region.US).characterBattlePets("Stormrage", "Ryken"))
                .isEqualTo("https://us.api.blizzard.com/wow/character/Stormrage/Ryken?fields=pets");
    }

}
