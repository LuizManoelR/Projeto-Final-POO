package br.ufs.garcomeletronico.dao;

import br.ufs.garcomeletronico.model.Comanda;

public class ComandaDAO extends BaseDAO<Comanda> {

    public ComandaDAO(){

        super("garcomeletronico/src/main/resources/data/comandas.json", Comanda.class);

    }

}
