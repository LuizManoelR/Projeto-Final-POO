package br.ufs.garcomeletronico.model;

import java.util.List;

public class ComandaAberta implements ComandaState {

    @Override
    public void adicionarItem(Comanda comanda, List<Item> itens){
        if (itens == null){
            throw new IllegalArgumentException("Item não pode ser nulo");
        }

        List<Item> pedidos = comanda.getPedidos();

        for(Item item: itens){

            if(!pedidos.stream().anyMatch(i -> i.equals(item))){

                pedidos.add(item);
                comanda.setPedidos(pedidos);

            }else {

               Item item2 = pedidos.stream()
                       .filter(i -> i.equals(item))
                       .findFirst()
                       .get();

                for(int i = 0; i < item.getQuantidade(); i++){

                    item2.add();
                }
                comanda.setPedidos(pedidos);
            }
    }


        }


    @Override
    public void removerItem(Comanda comanda, List<Item> itens) {

        List<Item> pedidos = comanda.getPedidos();

        for(Item item: itens){

            Item item2 = pedidos.stream()
                                .filter(i -> i.equals(item))
                                .findFirst()
                                .get();
            item2.remove();
            if(item2.getQuantidade() == 0){pedidos.remove(item2);}

        }
        comanda.setPedidos(pedidos);

    }

    @Override
    public void fecharComanda(Comanda comanda){
        comanda.setState(new ComandaFechada());
        System.out.println("Comanda " + comanda.getId() + " fechada. Total: R$ " + comanda.valorTotal());
    }
    
    @Override
    public String getStatus(){
        return "Aberta";
    }
}
