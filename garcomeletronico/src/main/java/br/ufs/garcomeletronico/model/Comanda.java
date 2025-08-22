package br.ufs.garcomeletronico.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import br.ufs.garcomeletronico.utils.BigDecimalUtils;

public class Comanda implements Identificavel {
    private String id;
    private List<Item> pedidos;
    private ComandaState state; //Estado atual
    private String mesaId; // Conectar com Mesa

    public Comanda(String id) {
        this.id = id;
        this.pedidos = new ArrayList<>();
        this.state = new ComandaAberta(); // Inicia sempre aberta
    }

    public Comanda(String id, String mesaId){
        this(id);   // Chama o construtor acima
        this.mesaId = mesaId;   // Adiciona mesa
    }

    public void setState(ComandaState state){
        this.state = state;
    }

    // Os métodos getPedidos() e getPedidosInternal() diferem no tipo de retorno,
    // um retorna uma cópia da lista e o outro a lista original, respectivamente

    public List<Item> getPedidosInternal(){
        return pedidos;
    }

    public List<Item> getPedidos(){
        return new ArrayList<>(pedidos); //  Retorna cópia para encapsulamento
    }

    @Override
    public String getId() {
        return id;
    }

    public String getStatus(){ return state.getStatus(); }

    public String getMesaId(){ return mesaId; }

    public void setMesaId(String mesaId){ this.mesaId = mesaId; }

    // Métodos que delegam para o estado atual
    public void adicionarItem(Item item) {
        state.adicionarItem(this, item);
    }

    public boolean removerItem(Item  item){
        return state.removerItem(this, item);
    }

    public boolean removerItem(int index) {
        if (index >= 0 && index < pedidos.size()){
            Item item = pedidos.get(index);
            return removerItem(item);
        }
        return false;   
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
        
        sb.append("=== COMANDA ").append(id).append(" ===\n");
        if (mesaId != null) {
            sb.append("Mesa: ").append(mesaId).append("\n");
        }
        sb.append("Status: ").append(getStatus()).append("\n");
        sb.append("Itens (").append(pedidos.size()).append("):\n");
        
        for (int i = 0; i < pedidos.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(pedidos.get(i)).append("\n");
        }
        
        sb.append("TOTAL: R$ ").append(String.format("%.2f", valorTotal()));
        
        return sb.toString();
    }

    public void exibir() {
        System.out.println(this);
    }
}