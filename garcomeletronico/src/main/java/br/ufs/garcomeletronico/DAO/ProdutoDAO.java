package br.ufs.garcomeletronico.dao;

import java.util.stream.Collectors;
import java.util.Comparator;

import br.ufs.garcomeletronico.model.Produto;


public class ProdutoDAO extends BaseDAO<Produto> {
    public ProdutoDAO() {//impletação apartir da base abstrata
        //path e class 
        super("garcomeletronico/data/produtos.json", Produto.class);
    }

    public synchronized void ordernar() {///ordena ao banco de dados dos produtos
            salvar(lista.stream()
                        .sorted(Comparator.comparing(Produto::getNome))
                        .collect(Collectors.toList()));

    }
    
    public static void main(String[] args) {
        
    }
}
