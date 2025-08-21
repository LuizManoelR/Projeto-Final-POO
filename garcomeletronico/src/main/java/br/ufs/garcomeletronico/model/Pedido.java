package br.ufs.garcomeletronico.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido implements Identificavel{
    private List<Item> itens;
    private Mesa mesa;
    private String id;
    private PedidoStatus status;

    public Pedido(List<Item> itens, Mesa mesa){
        this.itens = itens;
        this.mesa = mesa;
        this.status = PedidoStatus.PENDENTE;
    }

    public List<Item> getItens(){
        return new ArrayList<>(itens);
    }

    public String getStatus(){
        return status.getStatus();
    }

    public String getId(){return id;}

    public Mesa getMesa(){return mesa;}

    public void setStatus(String status){
        this.status = PedidoStatus.valueOf(status);
    }

    public void produzir(){setStatus("EM_PRODUCAO");}
    public void concluir(){setStatus("CONCLUIDO");}
    public void cancelar(){setStatus("CANCELADO");}


    @Override
    public String toString(){
        return String.format(
            "Itens : %s\n"+
            "Mesa  : %s\n"+
            "Status: %s\n",
            itens, getMesa().getId(), getStatus());
    }

    public void exibir(){System.out.println(this);}

}