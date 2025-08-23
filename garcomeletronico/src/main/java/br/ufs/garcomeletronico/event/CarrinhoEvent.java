package br.ufs.garcomeletronico.event;

import br.ufs.garcomeletronico.model.Carrinho;

// Representa o evento do carrinho (SALVO, CARREGADO, LIMPO…).
public class CarrinhoEvent {
    private final String acao;
    private final Carrinho carrinho;

    public CarrinhoEvent(String acao, Carrinho carrinho) {
        this.acao = acao;
        this.carrinho = carrinho;
    }

    public String getAcao() {
        return acao;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }
}
