package br.ufs.garcomeletronico.model;

public enum ProdutoCategories {

    ENTRADA("Entrada"),
    PRATOPRINCIPAL("Prato Principal"),
    BEBIDA("Bebida"),
    SOBREMESA("Sobremesa");

    private String categoria;

    ProdutoCategories(String categoria){

        this.categoria = categoria;

    }

    public String get(){return categoria;}

}
