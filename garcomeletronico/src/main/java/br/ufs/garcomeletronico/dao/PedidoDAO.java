package br.ufs.garcomeletronico.dao;

import br.ufs.garcomeletronico.model.Pedido;

public class PedidoDAO extends BaseDAO<Pedido> {

        public PedidoDAO(){//impletação apartir da base abstrata
        //path e class 

        super("garcomeletronico/data/pedidos.json", Pedido.class);

    }
    
}
