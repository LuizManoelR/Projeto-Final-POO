package br.ufs.projetopoo.controller;

import br.ufs.projetopoo.model.Produto;
import br.ufs.projetopoo.view.ProdutoView;

public class ProdutoController {
    private Produto model;
    private ProdutoView view;

    public ProdutoController(Produto model, ProdutoView view){
        this.model = model;
        this.view = view;
    }

    public void exibirProduto(){
        view.exibirProduto(model.getId(), model.getNome(), model.getDescricao(), model.getPreco());
    }

}
