package br.ufs.garcomeletronico.model;

import java.util.List;

public class ComandaAberta implements ComandaState {

    @Override
    public void adicionarItem(Comanda comanda, List<Item> itens){
        
        if (itens == null){
            throw new IllegalArgumentException("Item não pode ser nulo");
        }

        List<Item> pedidos = comanda.getPedidos();    

        for(Item itemNovo: itens){

            if(!pedidos.stream().anyMatch(i -> i.equals(itemNovo))){
                pedidos.add(itemNovo);
                
            }else {
                
               Item itemExistente = pedidos.stream()
                       .filter(i -> i.equals(itemNovo))
                       .findFirst()
                       .get();
                itemExistente.exibir();
                int c = itemNovo.getQuantidade();
                for(int i = 0; i < c; i++){

                    itemExistente.add();
                }
                
            }
            
        }

            comanda.setPedidos(pedidos);


        }


    @Override
    public void removerItem(Comanda comanda, Produto produto) {

        Item item = comanda.buscar(produto);
        List<Item> pedidos = comanda.getPedidos();

        if(item != null){

            if(item.getQuantidade() == 0){

                pedidos.remove(item);

            }else{item.remove();}

        }
        comanda.setPedidos(pedidos);

    }

    @Override
    public void fecharComanda(Comanda comanda){
        comanda.setState(new ComandaFechada());
        System.out.println("Comanda " + comanda.getId() + " fechada. Total: R$ " + comanda.valorTotal());
    }

        @Override
    public void abrirComanda(Comanda comanda){
        throw new IllegalStateException("A comanda já está aberta.");
    }

        @Override
    public void resetComanda(Comanda comanda){

        throw new IllegalStateException("A comanda precisa estar fechada");

    }

    
    @Override
    public String getStatus(){
        return "Aberta";
    }
}
