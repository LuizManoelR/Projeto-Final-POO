package br.ufs.garcomeletronico.controller;

import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.model.Item;

import java.math.BigDecimal;
import java.util.List;

public class CarrinhoController {
    private final Carrinho carrinho;

    public CarrinhoController(Mesa mesa) {
        this.carrinho = new Carrinho(mesa);
    }

    public CarrinhoController(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    // Adiciona 1 unidade do produto com a lógica de Carrinho
    public boolean addProduto(Produto produto) {
        if (produto == null) return false;
        carrinho.add(produto);
        return true;
    }

    // Remove 1 unidade do produto com a lógica de Carrinho)
    public boolean removeProduto(Produto produto) {
        if (produto == null) return false;
        if (carrinho.buscar(produto) == null) return false;
        carrinho.remove(produto);
        return true;
    }

    // Retorna cópia dos itens
    public List<Item> listarItens() {
        return carrinho.getCarrinho();
    }

    public int totalItens() {
        return carrinho.size();
    }

    public boolean estaVazio() {
        return carrinho.isEmpty();
    }

    public BigDecimal getValorTotal() {
        return carrinho.getValorTotal();
    }

    // Finaliza/pede e esvazia o carrinho
    public BigDecimal pedir() {
        return carrinho.pedir();
    }

    public Carrinho getModel() {
        return carrinho;
    }
}