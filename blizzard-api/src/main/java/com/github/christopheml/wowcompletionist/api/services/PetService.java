package com.github.christopheml.wowcompletionist.api.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.EndpointsResolver;
import com.github.christopheml.wowcompletionist.api.Region;
import com.github.christopheml.wowcompletionist.api.model.Character;
import com.github.christopheml.wowcompletionist.api.model.Pet;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final RestTemplate restTemplate;

    private final EndpointsResolver endpointsResolver;

    @Autowired
    public PetService(RestTemplate blizzardApiRestTemplate, EndpointsResolver endpointsResolver) {
        this.restTemplate = blizzardApiRestTemplate;
        this.endpointsResolver = endpointsResolver;
    }

    public Optional<Pets> forCharacter(CharacterIdentity characterIdentity) {
        String endpoint = endpointsResolver.forRegion(characterIdentity.getRegion())
                .characterBattlePets(characterIdentity.getRealm(), characterIdentity.getCharacter());

        Character characterData = restTemplate.getForObject(endpoint, Character.class);
        if (characterData == null || characterData.getPets() == null) {
            return Optional.empty();
        }
        return Optional.of(characterData.getPets());
    }

    public List<Pet> getAll(Region region) {
        String endpoint = endpointsResolver.forRegion(region).allPets();
        AllPetsResponse response = restTemplate.getForObject(endpoint, AllPetsResponse.class);
        if (response == null) {
            throw new RuntimeException("Region pet data unavailable");
        }
        return response.getPets();
    }

    public static class AllPetsResponse {

        @JsonProperty
        private List<Pet> pets;

        public List<Pet> getPets() {
            return pets;
        }

    }

}
