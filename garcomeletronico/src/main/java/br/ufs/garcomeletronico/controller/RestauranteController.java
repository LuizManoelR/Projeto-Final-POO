package br.ufs.garcomeletronico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestauranteController {

    

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cozinha")
    public String cozinha() {
        return "cozinha";
    }

    @GetMapping("/cozinha/garcom")
    public String cozinhaGarcom() {
        return "cozinha-garcom";
    }
}

