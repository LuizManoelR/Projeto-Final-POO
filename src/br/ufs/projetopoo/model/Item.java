package br.ufs.projetopoo.model;

import br.ufs.projetopoo.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class Item{
    // Atributos
    private Produto produto;
    private int qtd;

    // Construtor
    public Item(Produto produto, int qtd){
        this.produto = produto;
        this.qtd = qtd;
    }

    // Getters e Setters
    public Produto getProduto(){ return this.produto; }
    public int getQuantidade(){ return this.qtd; }
    public void setProduto(Produto produto){ this.produto = produto; }
    public void setQuantidade(int qtd){ this.qtd = qtd; }

    // Cálculo do valor total
    public BigDecimal valorTotal(){
        return BigDecimalUtils.multiplicar(this.qtd, produto.getPreco());
    }

    public String toString(){
        return String.format("%s (Qtd: %d) - Total: R$ %.2f",
                produto.getNome(),
                this.qtd,
                this.valorTotal().doubleValue());
    }

    // Exibição dos dados do objeto
    public void exibir(){
        System.out.println(this);
    }
}