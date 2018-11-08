package com.github.christopheml.wowcompletionist.web;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.Endpoints;
import com.github.christopheml.wowcompletionist.api.model.Character;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

@Service
public class BattlePetsService {

    private final OAuth2RestTemplate restTemplate;

    @Autowired
    public BattlePetsService(OAuth2RestTemplate blizzardApiRestTemplate) {
        this.restTemplate = blizzardApiRestTemplate;
    }

    @Cacheable("characterPets")
    public Pets fetch(CharacterIdentity characterIdentity) {
        String endpoint = Endpoints.forRegion(characterIdentity.getRegion())
                .battlePets(characterIdentity.getRealm(), characterIdentity.getCharacter());

        Character result = restTemplate.getForObject(endpoint, Character.class);

        if (result == null || result.getPets() == null) {
            // FIXME Handle case properly
            throw new RuntimeException("Failed to fetch battle pets for " + characterIdentity);
        }

        return result.getPets();
    }

}
