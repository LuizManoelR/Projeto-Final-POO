package br.ufs.garcomeletronico.controller;

import br.ufs.garcomeletronico.model.Item;
import br.ufs.garcomeletronico.service.CardapioService;
import br.ufs.garcomeletronico.model.Carrinho;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RestauranteController {

    private final CardapioService cardapioService;
    private final Carrinho carrinho;

    public RestauranteController(CardapioService cardapioService, Carrinho carrinho) {
        this.cardapioService = cardapioService;
        this.carrinho = carrinho;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q, Model model) {
        return listarPorCategoria(null, q, model);
    }

    @GetMapping("/categoria/{categoria}")
    public String listarPorCategoria(
            @PathVariable(required = false) String categoria,
            @RequestParam(required = false) String q,
            Model model) {

        List<Item> itens;

        if (categoria == null || categoria.isEmpty()) {
            itens = cardapioService.listarItens();
        } else {
            itens = cardapioService.listarPorCategoria(categoria);
        }

        if (q != null && !q.isEmpty()) {
            String busca = q.toLowerCase();
            itens = itens.stream()
                    .filter(i -> i.getNome().toLowerCase().contains(busca))
                    .collect(Collectors.toList());
        }

        model.addAttribute("itens", itens);
        model.addAttribute("categorias", cardapioService.listarCategorias());
        model.addAttribute("categoriaAtual", categoria);
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("mesa", "Mesa 002");
        model.addAttribute("q", q != null ? q : "");

        return "home";
    }
}