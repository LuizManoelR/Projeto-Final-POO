package br.ufs.garcomeletronico.model;

import br.ufs.garcomeletronico.utils.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    // Atributo
    private List<Item> carrinho;
    private String mesaId; // Conectar com Mesa

    // Construtores
    public Carrinho(){ this.carrinho = new ArrayList<>(); }
    
    public Carrinho(String mesaId){
        this.carrinho = new ArrayList<>();
        this.mesaId = mesaId;
    }

    //Getters
    public List<Item> getCarrinho(){ 
        return new ArrayList<> (this.carrinho); // Retorna cópia para encapsulamento
     }

    public String getMesaId(){ return this.mesaId; }
    public void setMesaId(String mesaId){ this.mesaId = mesaId; }
     

    // Calcular valor total do carrinho
    public BigDecimal getValorTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : carrinho){
            total = total.add(item.valorTotal());
        } return total;
    }

    // Métodos de verificação
    public boolean isEmpty(){ return this.carrinho.isEmpty(); } // Verificar se carrinho está vaziado

    public int size(){ return this.carrinho.size(); } // Verifica tamanho do carrinho

    public void add(Item item){ this.carrinho.add(item); } // Adicionar item

    // Métodos de modificação
    public void remove(Item item){ this.carrinho.remove(item); } //  Remover

    // Remoção de item por índice
    public void remove(int index){
        if (index >=0 && index < carrinho.size()){
            this.carrinho.remove(index);
        }
    }

    public void esvaziar(){ this.carrinho.clear(); }

    public BigDecimal pedir(){
        BigDecimal total = getValorTotal();
        System.out.println("Pedido feito! Total: R$ " + total);
        esvaziar();
        return total;
    }

    // Finalizar pedido - retorna Comanda
    public Comanda finalizarPedido(String comandaId) {
        if (isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar pedido com carrinho vazio");
        }
        
        // Criar comanda com ID e transferir itens
        Comanda comanda = new Comanda(comandaId);
        
        // Adicionar todos os itens do carrinho à comanda
        for (Item item : this.carrinho) {
            comanda.adicionarItem(item);
        }
        
        BigDecimal total = getValorTotal();
        System.out.println("Pedido finalizado! Total: R$ " + total);
        
        // Limpar carrinho após finalizar
        esvaziar();
        
        return comanda;
    }

    // Sobrecarga para gerar ID automaticamente
    public Comanda finalizarPedido() {
        String comandaId = gerarIdComanda();
        return finalizarPedido(comandaId);
    }

        // Gerar ID único para comanda
    private String gerarIdComanda() {
        return "CMD" + System.currentTimeMillis() + (mesaId != null ? "_" + mesaId : "");
    }
    
    // Método para conectar com Mesa
    public void vincularMesa(Mesa mesa) {
        this.mesaId = mesa.getId();
    }

    @Override
    public String toString() {
        if (carrinho.isEmpty()) { 
            return "Carrinho vazio" + (mesaId != null ? " - Mesa: " + mesaId : ""); 
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Carrinho").append(mesaId != null ? " - Mesa: " + mesaId : "").append("\n");
        
        for (int i = 0; i < carrinho.size(); i++) {
            sb.append(String.format("%d. %s%n", (i + 1), carrinho.get(i).toString()));
        }
        
        sb.append(String.format("TOTAL: R$ %.2f", getValorTotal()));
        return sb.toString();
    }
}
