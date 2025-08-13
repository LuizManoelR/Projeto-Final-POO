package br.ufs.projetopoo.model;

import br.ufs.projetopoo.utils.BigDecimalUtils;

public class Produto {
    private String id;
    private String nome;
    private String descricao;
    private BigDecimalUtils preco;

    public Produto(String id, String nome, String descricao, BigDecimalUtils preco){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getId(){ return id; }
    public String getNome(){ return nome; }
    public String getDescricao(){ return descricao; }
    public BigDecimalUtils getPreco(){  return preco; }

}
