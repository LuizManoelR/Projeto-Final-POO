package br.ufs.garcomeletronico.service;

import br.ufs.garcomeletronico.dao.ComandaDAO;
import br.ufs.garcomeletronico.model.Comanda;

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

        Comanda c = comandaDAO.buscarPorCodigo(id);

        c.fecharComanda();

    }

    public void abrirComanda(String id){

        Comanda c = comandaDAO.buscarPorCodigo(id);

        c.abrirComanda();

    }

    public void resetComanda(String id){

        Comanda c = comandaDAO.buscarPorCodigo(id);
        c.resetComanda();

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
