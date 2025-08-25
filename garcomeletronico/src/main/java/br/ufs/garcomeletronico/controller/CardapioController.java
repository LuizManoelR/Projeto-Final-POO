package br.ufs.garcomeletronico.controller;

import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.service.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio") // caminho base das requisições, i.e., todos endpoints terão cardapio no começo da URL
public class CardapioController {

    @Autowired // Spring injeta automaticamente a instância de CardapioService
    private CardapioService cardapioService; // usado para acessar os métodos que retornam produtos

    // Endpoint para listar todos os produtos
    @GetMapping
    public List<Produto> listarTodos() { 
        return cardapioService.listarProdutos(); // retonra lista de Produto em JSON
    }

    // Endpoint para listar apenas entradas
    @GetMapping("/entradas")
    public List<Produto> listarEntradas() {
        return cardapioService.listEntradas();
    }

    // Endpoint para listar apenas pratos principais
    @GetMapping("/pratos-principais")
    public List<Produto> listarPratosPrincipais() {
        return cardapioService.listPratosPrincipais();
    }

    // Endpoint para listar apenas bebidas
    @GetMapping("/bebidas")
    public List<Produto> listarBebidas() {
        return cardapioService.listBebidas();
    }

    // Endpoint para listar apenas sobremesas
    @GetMapping("/sobremesas")
    public List<Produto> listarSobremesas() {
        return cardapioService.listSobremesa();
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