package br.ufs.garcomeletronico.DAO;


import br.ufs.garcomeletronico.model.Produto;


public class ProdutoDAO extends BaseDAO<Produto> {
    public ProdutoDAO() {
        super("garcomeletronico/src/main/resources/data/produtos.json", Produto.class);
    }


    public static void main(String[] args) {
        
    }
}
