package br.ufs.garcomeletronico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufs.garcomeletronico.dao.PedidoDAO;
import br.ufs.garcomeletronico.model.Item;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Pedido;

@Service
public class PedidoService {

    private PedidoDAO pedidoDAO;

    public PedidoService(){pedidoDAO = new PedidoDAO();}

    public void criarPedido(List<Item> itens, Mesa mesa){

        Pedido pedido = new Pedido(itens, mesa);
        pedidoDAO.adicionar(pedido);

    }
    
}
