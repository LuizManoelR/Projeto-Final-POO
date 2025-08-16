package br.ufs.projetopoo.model;

import br.ufs.projetopoo.utils.BigDecimalUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    // Atributo
    private List<Item> carrinho;

    // Construtor
    public Carrinho(){ this.carrinho = new ArrayList<>(); }

    //Getter
    public List<Item> getCarrinho(){ return this.carrinho; }

    // Calcular valor total do carrinhp
    public BigDecimal getValorTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : carrinho){
            total = total.add(item.valorTotal());
        } return total;
    }

    // Métodos
    public boolean isEmpty(){ return this.carrinho.isEmpty(); } // Verificar se carrinho está vaziado

    public int size(){ return this.carrinho.size(); } // Verifica tamanho do carrinho

    public void add(Item item){ this.carrinho.add(item); } // Adicionar item

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

    @Override
    public String toString(){
        if (carrinho.isEmpty()){ return "Carrinho vazio"; }

        StringBuilder sb = new StringBuilder();
        sb.append("Carrinho: ");
        for (int i = 0; i < carrinho.size(); i++) {
            sb.append((i + 1)).append(". ").append(carrinho.get(i).toString()).append("\n");
        }
        sb.append("TOTAL: R$ ").append(getValorTotal());
        return sb.toString();
    }


}
