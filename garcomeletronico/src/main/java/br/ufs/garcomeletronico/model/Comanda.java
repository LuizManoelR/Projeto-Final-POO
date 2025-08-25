package br.ufs.garcomeletronico.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.ufs.garcomeletronico.utils.BigDecimalUtils;

public class Comanda implements Identificavel {
    private String id;
    private ComandaState state; //Estado atual
    private List<Item> pedidos;
    private static int ultimo = 1;

    public Comanda(){
    
        this.id = String.format("%04d", ultimo++);
        pedidos = new ArrayList<>();
        state = new ComandaLivre();

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
        
        List<Item> pedidoscpy = pedidos.stream()
            .map(i -> new Item(i.getProduto(), i.getQuantidade()))
            .collect(Collectors.toCollection(ArrayList::new));

        return pedidoscpy; //  Retorna cópia para encapsulamento
    }

    @Override
    public String getId() {
        return id;
    }

    public String getStatus(){ return state.getStatus(); }

        public Item buscar(Produto produto){

        return pedidos.stream()
            .filter(i -> i.getProduto().equals(produto))
            .findFirst()
            .orElse(null);

    }

    // Métodos que delegam para o estado atual
    public void adicionarItem(List<Item> itens) {
        state.adicionarItem(this,itens);
    }

    public void removerItem(Produto produto){
        
        state.removerItem(this, produto);

    }

    public void fecharComanda(){
        state.fecharComanda(this);
    }
   
    public void abrirComanda(){
        state.abrirComanda(this);
    }

    public void resetComanda(){
        state.resetComanda(this);
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
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
            "======== COMANDA =======\n"+
            "Id  : %s\n"+
            "Status: %s\n\n\n",
            id, getStatus()));
        
       for (int i = 0; i < pedidos.size(); i++) {
            sb.append(String.format("%d. %s%n\n\n", (i + 1), pedidos.get(i).toString()));
        }
        sb.append(String.format("Valor Total: %.2f\n", valorTotal()));
        
        return sb.toString();
    }

    public void exibir() {
        System.out.println(this);
    }
}