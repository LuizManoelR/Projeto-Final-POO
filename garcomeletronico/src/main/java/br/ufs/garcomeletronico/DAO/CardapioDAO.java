package br.ufs.garcomeletronico.DAO;


import java.math.BigDecimal;

import br.ufs.garcomeletronico.model.Produto;


public class ProdutoDAO extends BaseDAO<Produto> {
    public ProdutoDAO() {
        super("garcomeletronico/src/main/resources/data/produtos.json", Produto.class);
    }


    public static void main(String[] args) {

        Produto produto = new Produto("Café coado", "Café tradicional coado", new BigDecimal("4.00"));
        
        ProdutoDAO pDAO = new ProdutoDAO();

        pDAO.adicionar(produto);
    }
}
