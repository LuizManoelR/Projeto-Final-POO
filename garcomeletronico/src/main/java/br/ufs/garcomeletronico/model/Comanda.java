package br.ufs.garcomeletronico.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import br.ufs.garcomeletronico.utils.BigDecimalUtils;

public class Comanda implements Identificavel {
    private String id;
    private List<Item> pedidos;
    private ComandaState state; //Estado atual
    private static int ultimo = 1;

    public Comanda(){
    
        this.id = String.format("%04d", ultimo++);
        pedidos = new ArrayList<>();
        state = new ComandaFechada();

    }

    public void setState(ComandaState state){
        this.state = state;
    }

    public void setPedidos(List<Item> itens){

        this.pedidos = itens;

    }

    // Os métodos getPedidos() e getPedidosInternal() diferem no tipo de retorno,
    // um retorna uma cópia da lista e o outro a lista original, respectivamente

    public List<Item> getPedidos(){
        return new ArrayList<>(pedidos); //  Retorna cópia para encapsulamento
    }

    @Override
    public String getId() {
        return id;
    }

    public String getStatus(){ return state.getStatus(); }

    // Métodos que delegam para o estado atual
    public void adicionarItem(List<Item> itens) {
        state.adicionarItem(this, itens);
    }

    public void removerItem(List<Item>  item){
        
        state.removerItem(this, item);

    }

    public void fecharComanda(){
        state.fecharComanda(this);
    }

    public BigDecimal valorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : pedidos) {
            total = BigDecimalUtils.somar(total, item.valorTotal());
        }
        return total;
    }

    public int quantidadeItens(){ return pedidos.size(); }

    public boolean isEmpty(){ return pedidos.isEmpty(); }


     @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
    
        
        
        return sb.toString();
    }

    public void exibir() {
        System.out.println(this);
    }
}