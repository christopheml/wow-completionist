package com.github.christopheml.wowcompletionist.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EndpointsTest {

    @Test
    void battlePetsEndpoint() {
        String url = Endpoints.EUROPE.battlePets("Hyjal", "Malcorne");
        assertThat(url).isEqualTo("https://eu.api.blizzard.com/wow/character/Hyjal/Malcorne?fields=pets");
    }

}
