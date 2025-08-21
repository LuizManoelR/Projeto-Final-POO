package br.ufs.garcomeletronico.dao;

import br.ufs.garcomeletronico.model.Mesa;


public class MesaDAO extends BaseDAO<Mesa>{

        public MesaDAO(){

        super("garcomeletronico/src/main/resources/data/mesas.json", Mesa.class);

    }
    
}
