package br.ufs.garcomeletronico.model;

public class ComandaAberta implements ComandaState {

    @Override
    public void adicionarItem(Comanda comanda, Item item){
        if (item == null){
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        comanda.getPedidosInternal().add(item);
    }

    @Override
    public boolean removerItem(Comanda comanda, Item item) {
    return comanda.getPedidosInternal().remove(item);
}

    @Override
    public void fecharComanda(Comanda comanda){
        if (comanda.isEmpty()){
            throw new IllegalStateException("Não é possível fechar uma comanda vazia");
        }
        comanda.setState(new ComandaFechada());
        System.out.println("Comanda " + comanda.getId() + " fechada. Total: R$ " + comanda.valorTotal());
    }
    
    @Override
    public String getStatus(){
        return "Aberta";
    }
}
