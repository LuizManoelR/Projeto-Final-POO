package br.ufs.projetopoo.model;

import br.ufs.projetopoo.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class Produto implements Identificavel {
    private String id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private static int ultimo = 1;

    public Produto(String nome, String descricao, BigDecimal preco){
        this.id = String.format("P%04d", ultimo++);;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getId(){ return id; }
    public String getNome(){ return nome; }
    public String getDescricao(){ return descricao; }
    public BigDecimal getPreco(){  return preco; }

    public String toString(){

        return String.format(
        "Produto  : %s\n"+
        "Id       : %s\n"+
        "Descrição: %s\n"+
        "Preço  R$: %.2f\n"
        ,nome,id,descricao,preco);
    }

    public void exibir(){
        System.out.println(this);
    }
}
