package br.ufs.projetopoo.view;

import br.ufs.projetopoo.utils.BigDecimalUtils;

public class ProdutoView {
    public void exibirProduto(String id, String nome, String descricao, BigDecimalUtils preco){
        System.out.println("Id do produto: " + id);
        System.out.println("Produto: " + nome);
        System.out.println("Descrição: " + descricao);
        System.out.println("Preço: " + preco);
    }
}
