package br.ufs.garcomeletronico.model;



import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Produto implements Identificavel {
    private String id;
    private String nome;
    private String descricao;
    private ProdutoCategories categoria;
    private String img;
    private BigDecimal preco;
    private static int ultimo = 1;

    public Produto(String nome, String descricao,String categoria,BigDecimal preco){
        this.id = String.format("P%04d", ultimo++);
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = ProdutoCategories.valueOf(categoria);
        this.img = String.format("garcomeletronico/src/main/resources/static/img/" + id+".png");
        this.preco = preco;
    }

     @JsonCreator
    public Produto(
        @JsonProperty("id") String id,
        @JsonProperty("nome") String nome,
        @JsonProperty("descricao") String descricao,
        @JsonProperty("categoria") String categoria,
        @JsonProperty("img") String img,
        @JsonProperty("preco") BigDecimal preco
    ) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = ProdutoCategories.fromValue(categoria);
        this.img = img;
        this.preco = preco;
    }

        
    public String getId(){ return id; }
    public String getNome(){ return nome; }
    public String getDescricao(){ return descricao; }
    public String getCategoria(){ return categoria.get();}
    public String getImg(){return img;}
    public BigDecimal getPreco(){  return preco; }

    public void setNome(String nome){this.nome = nome;}
    public void setDescricao(String descricao){this.descricao = descricao;}
    public void setCategoria(String categoria){this.categoria = ProdutoCategories.fromValue(categoria);}
    public void setImg(String img){this.img = img;}
    public void setpreco(BigDecimal preco){this.preco = preco;}

        @Override
    public boolean equals(Object obj){

        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Produto outro = (Produto) obj;

        return this.getId().equals(outro.getId());

    }

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
