package com.github.christopheml.wowcompletionist.api.services;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.Endpoints;
import com.github.christopheml.wowcompletionist.api.EndpointsResolver;
import com.github.christopheml.wowcompletionist.api.Region;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PetServiceTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void character_battle_pets_api_call() {
        Endpoints endpoints = new Endpoints("http://localhost:" + wireMockServer.port());
        EndpointsResolver endpointsResolver = mock(EndpointsResolver.class);
        when(endpointsResolver.forRegion(any(Region.class))).thenReturn(endpoints);

        wireMockServer.stubFor(get(urlEqualTo("/wow/character/Hyjal/Giantstone?fields=pets"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/characterPets.json")));


        PetService petService = new PetService(new RestTemplate(), endpointsResolver);
        Optional<Pets> pets = petService.forCharacter(CharacterIdentity.of(Region.EU, "Hyjal", "Giantstone"));
        assertThat(pets).isNotEmpty();
        assertThat(pets.get().getCollected()).hasSize(10);
    }

}
