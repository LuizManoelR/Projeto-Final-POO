package br.ufs.garcomeletronico.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import br.ufs.garcomeletronico.utils.BigDecimalUtils;

public class Comanda implements Identificavel {
    private String id;
    private List<Item> pedidos;
    private String status;
    private String mesaId; // Conectar com Mesa

    public Comanda(String id) {
        this.id = id;
        this.pedidos = new ArrayList<>();
        this.status="Aberta";
    }

    public Comanda(String id, String mesaId){
        this(id);
        this.mesaId = mesaId;
    }

    @Override
    public String getId() {
        return id;
    }

    public List<Item> getPedidos(){
        return new ArrayList<>(pedidos); //  Retorna cópia para encapsulamento
    }

    public String getStatus(){ return status; }

    public void setStatus(String status){ this.status = status; }

    public String getMesaId(){ return mesaId; }

    public void setMesaId(String mesaId){ this.mesaId = mesaId; }


    public void adicionarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        if (!"Aberta".equals(status)) {
            throw new IllegalStateException("Não é possível adicionar itens a uma comanda " + status.toLowerCase());
        }
        pedidos.add(item);
    }

    public boolean removerItem(Item  item){
        if (!"Aberta".equals(status)){
            throw new IllegalStateException("Não é possível remover itens de uma comanda " + status.toLowerCase());
        }
        return pedidos.remove(item);
    }

        public boolean removerItem(int index) {
        if (!"Aberta".equals(status)) {
            throw new IllegalStateException("Não é possível remover itens de uma comanda " + status.toLowerCase());
        }
        if (index >= 0 && index < pedidos.size()) {
            pedidos.remove(index);
            return true;
        }
        return false;
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



    // Métodos de controle da comanda
    public void fecharComanda() {
        if (isEmpty()) {
            throw new IllegalStateException("Não é possível fechar uma comanda vazia");
        }
        this.status = "Fechada";
        System.out.println("Comanda " + id + " fechada. Total: R$ " + valorTotal());
    }

    public void reset() {
        if ("Fechada".equals(status)) {
            throw new IllegalStateException("Não é possível resetar uma comanda fechada");
        }
        pedidos.clear();
        this.status = "Aberta";
        System.out.println("Comanda " + id + " foi resetada e está vazia novamente.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=== COMANDA ").append(id).append(" ===\n");
        if (mesaId != null) {
            sb.append("Mesa: ").append(mesaId).append("\n");
        }
        sb.append("Status: ").append(status).append("\n");
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