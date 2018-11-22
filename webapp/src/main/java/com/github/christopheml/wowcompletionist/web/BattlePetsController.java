package com.github.christopheml.wowcompletionist.web;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.Region;
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

    @GetMapping("/{regionCode}/{realm}/{character}/pets")
    public String showBattlePets(@PathVariable String regionCode, @PathVariable String realm, @PathVariable String character, Model model) {
        Region region = Region.from(regionCode);
        CharacterIdentity characterIdentity = CharacterIdentity.of(region, realm, character);

        Pets pets = battlePetsService.fetch(characterIdentity);
        List<Pet> missingPets = petDatabase.missingPets(pets.getCollected(), characterIdentity.getRegion());

        model.addAttribute("collected", pets.getUniqueCollectedCount());
        model.addAttribute("total", pets.getTotalCount());
        model.addAttribute("missingPets", missingPets);
        model.addAttribute("characterIdentity", characterIdentity);
        return "battlePets";
    }

}
