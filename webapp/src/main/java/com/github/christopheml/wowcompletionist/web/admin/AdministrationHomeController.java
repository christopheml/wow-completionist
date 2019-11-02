package com.github.christopheml.wowcompletionist.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministrationHomeController {

    @GetMapping("/admin/home")
    public String administrationHome() {
        return "admin/home";
    }

}
