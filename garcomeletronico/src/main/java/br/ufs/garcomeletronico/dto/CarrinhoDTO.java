package br.ufs.garcomeletronico.dto;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CarrinhoDTO {
    public String mesaId;
    public List<ItemDTO> itens;

    public CarrinhoDTO(){
        this.itens = new ArrayList<>();
    }

    public static class ItemDTO{
        public String id;
        public String nome;
        public BigDecimal preco;
        public int quantidade;

        public ItemDTO(){}

        public ItemDTO(String id, String nome, BigDecimal preco, int quantidade){
            this.id = id;
            this.nome = nome;
            this.preco = preco;
            this.quantidade = quantidade;
        }
    }    
}
