package br.ufs.garcomeletronico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufs.garcomeletronico.dao.MesaDAO;
import br.ufs.garcomeletronico.model.Mesa;

@Service
public class MesaService {

    private MesaDAO mesaDAO;

    public MesaService(){mesaDAO = new MesaDAO();}
    
    public void criarMesa(){mesaDAO.adicionar(new Mesa());}
    
    public void criarMesa(int qtd){

            for(int i = 0; i < qtd; i++){
                criarMesa();
            }
    }

    public List<Mesa> garcomSolicitado(){ 
        
        return mesaDAO.listar()
                      .stream()
                      .filter(m -> m.getChamouGarcom().equals(true) )
                      .toList();

    }

}

