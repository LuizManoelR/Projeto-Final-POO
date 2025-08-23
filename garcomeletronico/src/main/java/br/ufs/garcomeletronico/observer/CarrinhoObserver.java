package br.ufs.garcomeletronico.observer;

import br.ufs.garcomeletronico.model.Carrinho;

/*
 * Padrão de projeto que permite um objeto notique o outros objetos sobre alterações em seu estado.
 */
public interface CarrinhoObserver {
    void onCarrinhoAlterado(String acao, Carrinho carrinho);
}