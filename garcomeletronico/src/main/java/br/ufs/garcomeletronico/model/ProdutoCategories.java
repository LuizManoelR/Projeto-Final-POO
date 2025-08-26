package br.ufs.garcomeletronico.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProdutoCategories {

    ENTRADA("Entrada"),
    PRATOPRINCIPAL("Prato Principal"),
    BEBIDA("Bebida"),
    SOBREMESA("Sobremesa");

    private String categoria;

    ProdutoCategories(String categoria){

        this.categoria = categoria;

    }
    @JsonValue
    public String get(){return categoria;}

}
