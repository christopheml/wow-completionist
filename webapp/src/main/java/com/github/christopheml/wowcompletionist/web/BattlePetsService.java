package com.github.christopheml.wowcompletionist.web;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import com.github.christopheml.wowcompletionist.api.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BattlePetsService {

    private final PetService petService;

    @Autowired
    public BattlePetsService(PetService petService) {
        this.petService = petService;
    }

    @Cacheable("characterPets")
    public Pets fetch(CharacterIdentity characterIdentity) {
        return petService.masterList(characterIdentity).orElseThrow(() -> new RuntimeException("No pet data found"));
    }

}
