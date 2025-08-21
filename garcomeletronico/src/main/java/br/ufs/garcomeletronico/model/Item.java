package br.ufs.garcomeletronico.model;

import br.ufs.garcomeletronico.utils.BigDecimalUtils;

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

    public void add(){setQuantidade(getQuantidade() + 1);}
    
    public void remove(){
        
        if(getQuantidade() != 0){

            setQuantidade(getQuantidade() - 1);

        }return;
    }

    // Cálculo do valor total
    public BigDecimal valorTotal(){
        return BigDecimalUtils.multiplicar(this.qtd, produto.getPreco());
    }

     //metodo especifico de comparação de itens
    @Override
    public boolean equals(Object obj){

        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Item outro = (Item) obj;

        return this.getProduto().equals(outro.getProduto());

    }

    public String toString(){
        return String.format
        ("Nome : %s\n"+ 
         "   Qtd  : %d\n" + 
         "   Total: R$ %.2f\n",
          produto.getNome(),
          this.qtd,
          this.valorTotal().doubleValue());
    }

    // Exibição dos dados do objeto
    public void exibir(){
        System.out.println(this);
    }
}