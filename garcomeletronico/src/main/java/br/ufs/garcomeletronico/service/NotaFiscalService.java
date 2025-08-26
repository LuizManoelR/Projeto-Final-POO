package br.ufs.garcomeletronico.service;

import org.springframework.stereotype.Service;

import br.ufs.garcomeletronico.dao.NotaFiscalDAO;
import br.ufs.garcomeletronico.model.Comanda;
import br.ufs.garcomeletronico.model.NotaFiscal;

@Service
public class NotaFiscalService {

    private NotaFiscalDAO notaFiscalDAO;

    public NotaFiscalService(){

        notaFiscalDAO = new NotaFiscalDAO();

    }

    
    public void gerarNotaFiscal(Comanda comanda){

        notaFiscalDAO.adicionar(new NotaFiscal(comanda));

    }

}
