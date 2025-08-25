package br.ufs.garcomeletronico.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.ufs.garcomeletronico.dao.ProdutoDAO;
import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.utils.BigDecimalUtils;

@Service

public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService(){this.produtoDAO = new ProdutoDAO();}

    public void criarProduto(String nome, String descricao, String categoria ,Object preco ){

        BigDecimal _preco = BigDecimalUtils.toBigDecimal(preco);

        if(_preco.floatValue() > 0){

            produtoDAO.adicionar(new Produto(nome, descricao, categoria,_preco));

        }else throw new IllegalArgumentException("Preço deve ser maior que zero");


    }

}
