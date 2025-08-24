package br.ufs.garcomeletronico.model;

import java.util.List;

public class ComandaLivre implements ComandaState {

     @Override
    public void adicionarItem(Comanda comanda, List<Item> itens){
        throw new IllegalStateException("Não é possível adicionar itens a uma comanda livre");
    }

    @Override
    public void removerItem(Comanda comanda, Produto produto){
        throw new IllegalStateException("Não é possível remover itens de uma comanda Livre ");
    }

    @Override
    public void fecharComanda(Comanda comanda){
        throw new IllegalStateException("Não é possível fechar uma comanda Livre");
    }
    @Override
    public void abrirComanda(Comanda comanda){
        
        comanda.setState(new ComandaAberta());

    }

    @Override
    public void resetComanda(Comanda comanda){

        throw new IllegalStateException("Não é possível resetar uma comanda Livre");

    }

    @Override
    public String getStatus(){
        return "Livre";
    }

}
