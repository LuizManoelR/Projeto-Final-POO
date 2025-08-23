package br.ufs.garcomeletronico.dao;

import br.ufs.garcomeletronico.model.Mesa;


public class MesaDAO extends BaseDAO<Mesa>{

        public MesaDAO(){//impletação apartir da base abstrata
        //path e class 

        super("garcomeletronico/data/mesas.json", Mesa.class);

    }
    
}
