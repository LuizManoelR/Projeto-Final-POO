package br.ufs.garcomeletronico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufs.garcomeletronico.dao.ComandaDAO;
import br.ufs.garcomeletronico.model.Comanda;

@Service
public class ComandaService {

    private ComandaDAO comandaDAO;

    public ComandaService(){comandaDAO = new ComandaDAO();}

    public void criar(){

        comandaDAO.adicionar(new Comanda());

    }

    public void criar(int qtd){

        for(int i = 0; i < qtd; i++){
            
            criar();

        }

    }

    public void fecharComanda(String id){

        List<Comanda> cmds = comandaDAO.listar();
        int index = comandaDAO.buscarIndex(id);
        Comanda c = cmds.get(index);
        c.fecharComanda();
        comandaDAO.salvar(cmds);

    }

    public void abrirComanda(String id){

        List<Comanda> cmds = comandaDAO.listar();
        int index = comandaDAO.buscarIndex(id);
        Comanda c = cmds.get(index);
        c.abrirComanda();
        comandaDAO.salvar(cmds);
    }

    public void resetComanda(String id){
        
        List<Comanda> cmds = comandaDAO.listar();
        int index = comandaDAO.buscarIndex(id);
        Comanda c = cmds.get(index);
        c.resetComanda();
        comandaDAO.salvar(cmds);

    }

    public Comanda buscarLivre(){

        Comanda c = comandaDAO.listar()
                              .stream()
                              .filter(i -> i.getStatus().equals("Livre"))
                              .findFirst()
                              .get();

     
        return c;

    }
    
}
