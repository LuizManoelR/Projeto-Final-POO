package br.ufs.garcomeletronico.model;

public class ComandaFechada implements ComandaState {
    @Override
    public void adicionarItem(Comanda comanda, Item item){
        throw new IllegalStateException("Não é possível adicionar itens a uma comanda fechada");
    }

    @Override
    public boolean removerItem(Comanda comanda, Item item){
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
