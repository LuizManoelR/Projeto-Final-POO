package br.ufs.garcomeletronico.dao;


import br.ufs.garcomeletronico.model.NotaFiscal;


public class NotaFiscalDAO extends BaseDAO<NotaFiscal>  {

    public NotaFiscalDAO(){//impletação apartir da base abstrata
        //path e class 

        super("garcomeletronico/data/notasfiscais.json", NotaFiscal.class);

    }

    public static void main(String[] args) {
        
                

    }
    
}
