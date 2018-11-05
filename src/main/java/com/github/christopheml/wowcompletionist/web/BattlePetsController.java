package com.github.christopheml.wowcompletionist.web;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.model.Pet;
import com.github.christopheml.wowcompletionist.api.model.Pets;
import com.github.christopheml.wowcompletionist.api.reference.PetDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BattlePetsController {

    private BattlePetsService battlePetsService;

    private PetDatabase petDatabase;

    @Autowired
    public BattlePetsController(BattlePetsService battlePetsService, PetDatabase petDatabase) {
        this.battlePetsService = battlePetsService;
        this.petDatabase = petDatabase;
    }

    @GetMapping("/{region}/{realm}/{character}/pets")
    public String showBattlePets(@PathVariable String region, @PathVariable String realm, @PathVariable String character, Model model) {
        CharacterIdentity characterIdentity = CharacterIdentity.of(region, realm, character);

        Pets pets = battlePetsService.fetch(characterIdentity);

        model.addAttribute("collected", pets.getUniqueCollectedCount());
        model.addAttribute("total", pets.getTotalCount());

        List<Pet> missingPets = petDatabase.missingPets(pets.getCollected());
        model.addAttribute("missingPets", missingPets);
        model.addAttribute("realm", realm);
        model.addAttribute("player", character);
        return "battlePets";
    }

}
