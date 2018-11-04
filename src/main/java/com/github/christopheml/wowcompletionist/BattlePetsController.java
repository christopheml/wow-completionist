package com.github.christopheml.wowcompletionist;

import com.github.christopheml.wowcompletionist.api.Endpoints;
import com.github.christopheml.wowcompletionist.api.model.Character;
import com.github.christopheml.wowcompletionist.api.model.Pet;
import com.github.christopheml.wowcompletionist.api.reference.PetDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BattlePetsController {

    private OAuth2RestTemplate restTemplate;

    private PetDatabase petDatabase;

    @Autowired
    public BattlePetsController(OAuth2RestTemplate restTemplate, PetDatabase petDatabase) {
        this.restTemplate = restTemplate;
        this.petDatabase = petDatabase;
    }

    @GetMapping("/pets/{realm}/{player}")
    public String showBattlePets(@PathVariable String realm, @PathVariable String player, Model model) {
        Character result = restTemplate.getForObject(Endpoints.EUROPE.battlePets(realm, player), Character.class);
        if (result != null && result.getPets() != null) {
            long unique = result.getPets().getUniqueCollectedCount();
            long total = unique + result.getPets().getUncollectedCount();
            model.addAttribute("collected", unique);
            model.addAttribute("total", total);

            List<Pet> missingPets = petDatabase.missingPets(result.getPets().getCollected());
            model.addAttribute("missingPets", missingPets);
        }
        model.addAttribute("realm", realm);
        model.addAttribute("player", player);
        return "battlePets";
    }

}
