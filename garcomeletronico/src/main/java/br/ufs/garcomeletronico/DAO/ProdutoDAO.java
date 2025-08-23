package br.ufs.garcomeletronico.dao;


import br.ufs.garcomeletronico.model.Produto;


public class ProdutoDAO extends BaseDAO<Produto> {
    public ProdutoDAO() {//impletação apartir da base abstrata
        //path e class 
        super("garcomeletronico/data/produtos.json", Produto.class);
    }


    public static void main(String[] args) {
        
    }
}
