package br.ufs.projetopoo.app;

import br.ufs.projetopoo.model.Produto;
import br.ufs.projetopoo.controller.ProdutoController;
import br.ufs.projetopoo.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws Exception {
        // Criação de um novo produto
        Produto produto = new Produto("Café coado", "Café tradicional coado", new BigDecimal("4.00"));
        produto.exibir();
    }
}
