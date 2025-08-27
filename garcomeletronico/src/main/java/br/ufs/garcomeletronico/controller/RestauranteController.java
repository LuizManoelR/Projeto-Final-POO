package br.ufs.garcomeletronico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestauranteController {

    @GetMapping("/")
    public String test(Model model) {
        // Thymeleaf vai renderizar a página, JS vai carregar produtos via REST
        return "teste";
    }
}