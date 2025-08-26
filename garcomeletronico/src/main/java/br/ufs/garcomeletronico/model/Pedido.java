package br.ufs.garcomeletronico.model;


import java.util.List;


public class Pedido implements Identificavel{
    private List<Item> itens;
    private Mesa mesa;
    private String id;
    private PedidoStatus status;
    private static int ultimo = 1;

    public Pedido(List<Item> itens, Mesa mesa){
        this.id = String.format("PD%04d", ultimo++);
        this.itens = itens;
        this.mesa = mesa;
        this.status = PedidoStatus.PENDENTE;
    }

    public List<Item> getItens(){
        
        List<Item> itenscpy = itens.stream()
            .map(i -> new Item(i.getProduto(), i.getQuantidade()))
            .toList();

        return itenscpy; //  Retorna cópia para encapsulamento
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
    public boolean equals(Object obj){

        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Pedido outro = (Pedido) obj;

        return this.getId().equals(outro.getId());

    }

    @Override
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
            "======== PEDIDO =======\n"+
            "Mesa  : %s\n"+
            "Status: %s\n\n\n",
            getMesa().getId(), getStatus()));
        
       for (int i = 0; i < itens.size(); i++) {
            sb.append(String.format("%d. %s%n\n\n", (i + 1), itens.get(i).toString()));
        }
        
        return sb.toString();
    }

    public void exibir(){System.out.println(this);}

}