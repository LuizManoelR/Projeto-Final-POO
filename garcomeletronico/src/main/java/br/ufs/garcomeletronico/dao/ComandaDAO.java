package br.ufs.garcomeletronico.dao;

import br.ufs.garcomeletronico.model.Comanda;

public class ComandaDAO extends BaseDAO<Comanda> {

    public ComandaDAO(){//impletação apartir da base abstrata
        //path e class 
        super("garcomeletronico/data/comandas.json", Comanda.class);

    }

}
