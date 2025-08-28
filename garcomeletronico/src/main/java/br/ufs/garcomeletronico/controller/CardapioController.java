package br.ufs.garcomeletronico.controller;

import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.service.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cardapio") // caminho base das requisições, i.e., todos endpoints terão cardapio no começo da URL
public class CardapioController {

    @Autowired // Spring injeta automaticamente a instância de CardapioService
    private CardapioService cardapioService; // usado para acessar os métodos que retornam produtos

    // Endpoint para listar todos os produtos
   @GetMapping
    public List<Produto> listartodos() {
        return cardapioService.listarProdutos();
    }
   @GetMapping("/categoria/{categoria}")
    public List<Produto> listarPorCategoria(@PathVariable String categoria) {
        return switch (categoria.toLowerCase()) {
            case "entradas" -> cardapioService.listEntradas();
            case "pratos-principais" -> cardapioService.listPratosPrincipais();
            case "bebidas" -> cardapioService.listBebidas();
            case "sobremesas" -> cardapioService.listSobremesa();
            default -> cardapioService.listarProdutos();
        };
    }

    // Endpoint para buscar produtos por nome
    @GetMapping("/buscar")
    public List<Produto> buscarPorNome(@RequestParam String nome) { 
        /* Recebe um parâmetro de query nome, ex.: /cardapio/buscar?nome=café  */
        return cardapioService.listarProdutos().stream() // Filtra a lista e retorna em JSON
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }
}
