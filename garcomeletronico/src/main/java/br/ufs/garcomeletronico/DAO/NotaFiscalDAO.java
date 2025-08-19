package br.ufs.garcomeletronico.DAO;


import br.ufs.garcomeletronico.model.NotaFiscal;


public class NotaFiscalDAO extends BaseDAO<NotaFiscal>  {

    public NotaFiscalDAO(){

        super("garcomeletronico/src/main/resources/data/notasfiscais.json", NotaFiscal.class);

    }

    public static void main(String[] args) {
        
                

    }
    
}
