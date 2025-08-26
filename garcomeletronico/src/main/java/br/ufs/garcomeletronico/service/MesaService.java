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

    public void mudarStatus(String id){

        List<Mesa> mesas = mesaDAO.listar();
        int index = mesaDAO.buscarIndex(id);
        Mesa m = mesas.get(index);
        m.mudarStatus();
        mesaDAO.salvar(mesas);

    }

    public Mesa iniciar(){

        Mesa mesa = buscarMesasLivres().stream()
                                       .findFirst()
                                       .get();

        mudarStatus(mesa.getId());

        return mesa;

    }

    public List<Mesa> buscarMesasLivres(){

        return mesaDAO.listar()
                      .stream()
                      .filter(m -> m.getStatus().equals("Livre"))
                      .toList();

    }

}

