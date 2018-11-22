package com.github.christopheml.wowcompletionist.web;

import com.github.christopheml.wowcompletionist.api.CharacterIdentity;
import com.github.christopheml.wowcompletionist.api.Region;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LandingController {

    @GetMapping("/")
    public String landingPage(Model model) {

        model.addAttribute("regions", Region.values());

        return "landing";
    }

    @PostMapping("/")
    public String landingPage(@RequestParam String regionCode, @RequestParam String realm, @RequestParam String character) {
        return "redirect:/" + regionCode + "/" + realm.toLowerCase() + "/" + character.toLowerCase() + "/";
    }

    @GetMapping("/{regionCode}/{realm}/{character}")
    public String landingPage(@PathVariable String regionCode, @PathVariable String realm, @PathVariable String character, Model model) {
        Region region = Region.from(regionCode);
        CharacterIdentity characterIdentity = CharacterIdentity.of(region, realm, character);

        model.addAttribute("characterIdentity", characterIdentity);

        return "index";
    }

}
