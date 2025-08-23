package br.ufs.garcomeletronico.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    // Atributo
    private List<Item> carrinho;
    private Mesa mesa; // Conectar com Mesa
    
    public Carrinho(Mesa mesa){
        this.carrinho = new ArrayList<>();
        this.mesa = mesa;
    }

    //Getters
    public List<Item> getCarrinho(){ 
        return new ArrayList<> (this.carrinho); // Retorna cópia para encapsulamento
     }

    public Mesa getMesa(){ return this.mesa; }
    public void setMesaId(Mesa mesa){ this.mesa = mesa; }
     

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

    public void add(Produto produto){ // Adicionar item 
        
        if(!carrinho.stream().anyMatch(item -> item.getProduto()
                                                   .equals(produto))){

            this.carrinho.add(new Item(produto, 1)); 

        }else buscar(produto).add();
    }

    public void remove(Produto produto){
        
         Item item = buscar(produto);
            

        if(item != null){

            if(item.getQuantidade() == 0){

                carrinho.remove(item);

            }else buscar(produto).remove();

        }
    }

    public Item buscar(Produto produto){

        return carrinho.stream()
            .filter(i -> i.getProduto().equals(produto))
            .findFirst()
            .orElse(null);

    }

    
    public void esvaziar(){ this.carrinho.clear(); }

    public BigDecimal pedir(){
        BigDecimal total = getValorTotal();
        System.out.println("Pedido feito! Total: R$ " + total);
        esvaziar();
        return total;
    }

   @Override
     public String toString() {
        if (carrinho.isEmpty()) { 
            return "Carrinho vazio"; 
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Carrinho").append(" - Mesa: " + mesa.getId()).append("\n");
        
        for (int i = 0; i < carrinho.size(); i++) {
            sb.append(String.format("%d. %s%n\n", (i + 1), carrinho.get(i).toString()));
        }
        
        sb.append(String.format("TOTAL: R$ %.2f\n", getValorTotal()));
        return sb.toString();
    }

    public void exibir(){System.out.println(this);}

    public static void main(String[] args) {
        



    }


}
