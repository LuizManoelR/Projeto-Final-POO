package br.ufs.garcomeletronico.service;

import org.springframework.stereotype.Service;
import java.util.List;

import br.ufs.garcomeletronico.dao.ProdutoDAO;
import br.ufs.garcomeletronico.model.Cardapio;
import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.model.ProdutoCategories;

@Service
public class CardapioService {

    private ProdutoDAO produtoDAO;

    private Cardapio cardapio;

    public CardapioService(){

        produtoDAO = new ProdutoDAO();

        cardapio = Cardapio.getInstancia();

        atualizar();

    }

    public void atualizar(){cardapio.atualizar(produtoDAO.listar());}

    public List<Produto> filtrarCategoria(String categoria){
    
        ProdutoCategories c = ProdutoCategories.valueOf(categoria);

        return cardapio.listar()
                       .stream()
                       .filter(p -> p.getCategoria().equals(c.get()))
                       .toList();
    

    }
    
    public List<Produto> listEntradas(){return filtrarCategoria("ENTRADA");}
    public List<Produto> listPratosPrincipais(){return filtrarCategoria("PRATOPRINCIPAL");}
    public List<Produto> listBebidas(){return filtrarCategoria("BEBIDA");}
    public List<Produto> listSobremesa(){return filtrarCategoria("SOBREMESA");}


    
}
