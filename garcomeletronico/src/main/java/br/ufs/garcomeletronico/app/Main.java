package br.ufs.garcomeletronico.app;

import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws Exception {
        // Criação de um novo produto
        Produto produto = new Produto("Café coado", "Café tradicional coado", new BigDecimal("4.00"));
        produto.exibir();
    }
}
