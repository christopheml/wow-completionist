package com.github.christopheml.wowcompletionist.api.services;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.Endpoints;
import com.github.christopheml.wowcompletionist.api.model.Character;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class PetService {

    private final RestTemplate restTemplate;

    @Autowired
    public PetService(RestTemplate blizzardApiRestTemplate) {
        this.restTemplate = blizzardApiRestTemplate;
    }

    public Optional<Pets> forCharacter(CharacterIdentity characterIdentity) {
        String endpoint = Endpoints.forRegion(characterIdentity.getRegion())
                .battlePets(characterIdentity.getRealm(), characterIdentity.getCharacter());

        Character characterData = restTemplate.getForObject(endpoint, Character.class);
        if (characterData == null || characterData.getPets() == null) {
            return Optional.empty();
        }
        return Optional.of(characterData.getPets());
    }

}
