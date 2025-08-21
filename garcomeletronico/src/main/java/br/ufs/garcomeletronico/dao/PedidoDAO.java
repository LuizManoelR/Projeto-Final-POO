package br.ufs.garcomeletronico.dao;

import br.ufs.garcomeletronico.model.Pedido;

public class PedidoDAO extends BaseDAO<Pedido> {

        public PedidoDAO(){

        super("garcomeletronico/src/main/resources/data/pedidos.json", Pedido.class);

    }
    
}
