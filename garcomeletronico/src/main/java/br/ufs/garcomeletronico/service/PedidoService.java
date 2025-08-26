package br.ufs.garcomeletronico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufs.garcomeletronico.dao.PedidoDAO;
import br.ufs.garcomeletronico.model.Item;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Pedido;
import br.ufs.garcomeletronico.model.PedidoStatus;

@Service
public class PedidoService {

    private PedidoDAO pedidoDAO;

    public PedidoService(){pedidoDAO = new PedidoDAO();}

    public void criarPedido(List<Item> itens, Mesa mesa){

        Pedido pedido = new Pedido(itens, mesa);
        pedidoDAO.adicionar(pedido);

    }

    public List<Pedido> filtrarStatus(String categoria){
    
        PedidoStatus s = PedidoStatus.valueOf(categoria);

        return pedidoDAO.listar()
                       .stream()
                       .filter(p -> p.getStatus().equals(s.getStatus()))
                       .toList();
    }

    public List<Pedido> listarPendente(){

        return filtrarStatus("PENDENTE");
    }
    
    public List<Pedido> listarEmProducao(){

        return filtrarStatus("EM_PRODUCAO");
    }
    
    public List<Pedido> listarConcluido(){

        return filtrarStatus("CONCLUIDO");
    }
    
    public List<Pedido> listarCancelado(){

        return filtrarStatus("CANCELADO");
    }

    public void pedidoColetado(String id){

        pedidoDAO.remover(id);

    }

    public void produzir(String id){

        List<Pedido> pedidos = pedidoDAO.listar();
        int index = pedidoDAO.buscarIndex(id);
        Pedido pedido = pedidos.get(index);
        pedido.produzir();
        pedidoDAO.salvar(pedidos);

    }
    public void concluir(String id){

        List<Pedido> pedidos = pedidoDAO.listar();
        int index = pedidoDAO.buscarIndex(id);
        Pedido pedido = pedidos.get(index);
        pedido.concluir();
        pedidoDAO.salvar(pedidos);

    }
    public void cancelar(String id){

        List<Pedido> pedidos = pedidoDAO.listar();
        int index = pedidoDAO.buscarIndex(id);
        Pedido pedido = pedidos.get(index);
        pedido.cancelar();
        pedidoDAO.salvar(pedidos);

    }

    public void removeCancelados(){

        pedidoDAO.remover(listarCancelado());

    }
    public void removeConcluidos(){

        pedidoDAO.remover(listarConcluido());

    }
    
}
