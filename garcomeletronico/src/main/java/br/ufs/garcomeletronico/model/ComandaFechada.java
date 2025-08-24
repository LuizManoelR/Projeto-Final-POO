package br.ufs.garcomeletronico.model;

import java.util.ArrayList;
import java.util.List;

public class ComandaFechada implements ComandaState {
    @Override
    public void adicionarItem(Comanda comanda, List<Item> itens){
        throw new IllegalStateException("Não é possível adicionar itens a uma comanda fechada");
    }

    @Override
    public void removerItem(Comanda comanda, Produto produto){
        throw new IllegalStateException("Não é possível remover itens de uma comanda fechada");
    }

    @Override
    public void fecharComanda(Comanda comanda){
        throw new IllegalStateException("A comanda já está fechada.");
    }
    @Override
    public void abrirComanda(Comanda comanda){
        
        throw new IllegalStateException("Não é possível abrir uma comanda fechada");

    }

    @Override
    public void resetComanda(Comanda comanda){

        comanda.setPedidos(new ArrayList<>());
        comanda.setState(new ComandaLivre());

    }

    @Override
    public String getStatus(){
        return "Fechada";
    }
    
}
