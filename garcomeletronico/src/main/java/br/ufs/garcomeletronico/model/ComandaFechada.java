package br.ufs.garcomeletronico.model;

import java.util.List;

public class ComandaFechada implements ComandaState {
    @Override
    public void adicionarItem(Comanda comanda, List<Item> itens){
        throw new IllegalStateException("Não é possível adicionar itens a uma comanda fechada");
    }

    @Override
    public void removerItem(Comanda comanda, List<Item> itens){
        throw new IllegalStateException("Não é possível remover itens de uma comanda fechada");
    }

    @Override
    public void fecharComanda(Comanda comanda){
        throw new IllegalStateException("A comanda já está fechada.");
    }

    @Override
    public String getStatus(){
        return "Fechada";
    }
    
}
