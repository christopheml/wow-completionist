package com.github.christopheml.wowcompletionist.api.services;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.Endpoints;
import com.github.christopheml.wowcompletionist.api.EndpointsResolver;
import com.github.christopheml.wowcompletionist.api.Region;
import com.github.christopheml.wowcompletionist.api.model.Pet;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This tests the PetService calls against simulated Blizzard API endpoints.
 */
class PetServiceTest {

    private WireMockServer wireMockServer;

    private PetService petService;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        EndpointsResolver endpointsResolver = createLocalEndpointsResolver();

        petService = new PetService(new RestTemplate(), endpointsResolver);
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void character_battle_pets_api_call() {
        wireMockServer.stubFor(get(urlEqualTo("/wow/character/Hyjal/Giantstone?fields=pets"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/characterPets.json")));

        Optional<Pets> pets = petService.forCharacter(CharacterIdentity.of(Region.EU, "Hyjal", "Giantstone"));
        assertThat(pets).isNotEmpty();
        assertThat(pets.get().getCollected()).hasSize(10);
    }

    @Test
    void all_pets_api_call() {
        wireMockServer.stubFor(get(urlEqualTo("/wow/pet/"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/allPets.json")));

        List<Pet> pets = petService.getAll(Region.EU);
        assertThat(pets).extracting(Pet::getName).containsExactly("Abyssal Eel", "Abyssius", "Accursed Hexxer", "Adventurous Hopling");
    }

    private EndpointsResolver createLocalEndpointsResolver() {
        Endpoints endpoints = new Endpoints("http://localhost:" + wireMockServer.port());
        EndpointsResolver endpointsResolver = mock(EndpointsResolver.class);
        when(endpointsResolver.forRegion(any(Region.class))).thenReturn(endpoints);
        return endpointsResolver;
    }

}
