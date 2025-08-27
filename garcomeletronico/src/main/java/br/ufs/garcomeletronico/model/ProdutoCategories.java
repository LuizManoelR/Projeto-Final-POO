package br.ufs.garcomeletronico.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProdutoCategories {
    @JsonProperty("Entrada")
    ENTRADA("Entrada"),
    @JsonProperty("Prato Principal")
    PRATOPRINCIPAL("Prato Principal"),
    @JsonProperty("Bebida")
    BEBIDA("Bebida"),
    @JsonProperty("Sobremesa")
    SOBREMESA("Sobremesa");

    private String categoria;

    ProdutoCategories(String categoria){

        this.categoria = categoria;

    }
    @JsonValue
    public String get(){return categoria;}

     @JsonCreator
    public static ProdutoCategories fromValue(String value) {
        for (ProdutoCategories c : ProdutoCategories.values()) {
            if (c.categoria.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + value);
    }

}
