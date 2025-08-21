package br.ufs.garcomeletronico.app;

import br.ufs.garcomeletronico.model.Carrinho;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Pedido;
import br.ufs.garcomeletronico.model.Produto;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws Exception {
        // Criação de um novo produto
        Produto produto = new Produto("Café coado", "Café tradicional coado", "img" ,new BigDecimal("4.00"));
        produto.exibir();

        Mesa mesa = new Mesa();

        Carrinho carrinho = new Carrinho(mesa);

        mesa.exibir();

        carrinho.add(produto);
        carrinho.add(produto);
        carrinho.add(produto);
        carrinho.add(produto);
        carrinho.add(produto);

        carrinho.exibir();

        Pedido pedido = new Pedido(carrinho.getCarrinho(), mesa);

        pedido.produzir();

        pedido.exibir();
 }
}
