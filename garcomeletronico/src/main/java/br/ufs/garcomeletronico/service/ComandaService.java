package br.ufs.garcomeletronico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufs.garcomeletronico.dao.ComandaDAO;
import br.ufs.garcomeletronico.dao.ProdutoDAO;
import br.ufs.garcomeletronico.model.Comanda;
import br.ufs.garcomeletronico.model.Item;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ComandaService {

    private NotaFiscalService notaFiscalService;

    private ComandaDAO comandaDAO;
    private ComandaCookieService comandaCookieService;

    public ComandaService(ComandaCookieService comandaCookieService){
        comandaDAO = new ComandaDAO();
        this.comandaCookieService = comandaCookieService;
        this.notaFiscalService = new NotaFiscalService();
    }

    public void criar(){

        comandaDAO.adicionar(new Comanda());

    }

    public void criar(int qtd){

        for(int i = 0; i < qtd; i++){
            
            criar();

        }

    }


    
    public void removerItem(String idC, String idP){
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Comanda> cmds = comandaDAO.listar();
        int index = comandaDAO.buscarIndex(idC);
        Comanda c = cmds.get(index);
        c.removerItem(produtoDAO.buscarPorCodigo(idP));
        comandaDAO.salvar(cmds);
    }
    public void adicionarItem(String id, List<Item> itens){

        List<Comanda> cmds = comandaDAO.listar();
        int index = comandaDAO.buscarIndex(id);
        Comanda c = cmds.get(index);
        c.adicionarItem(itens);
        comandaDAO.salvar(cmds);
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
        if (index == -1) {
            throw new RuntimeException("Comanda não encontrada: " + id);
        }
        Comanda c = cmds.get(index);
        c.abrirComanda();
        comandaDAO.salvar(cmds);
    }

    public void resetComanda(String id){
        
        List<Comanda> cmds = comandaDAO.listar();
        int index = comandaDAO.buscarIndex(id);
        Comanda c = cmds.get(index);
        notaFiscalService.gerarNotaFiscal(c);
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

    public void iniciar(HttpServletResponse response){

        Comanda livre = buscarLivre();
            if (livre == null) {
                System.err.println("Nenhuma comanda livre disponível.");
                return; // ou lançar uma exceção
            }

            comandaCookieService.salvar(response, livre.getId());
            System.out.println("Comanda iniciada: " + livre.getId());

    }
    public void limpar(HttpServletResponse response){
        comandaCookieService.limpar(response);
    }

    public String resgatarId(HttpServletRequest request){

        return comandaCookieService.resgatar(request);

    }

    public Comanda buscar(HttpServletRequest request){

        String id = resgatarId(request);

        return comandaDAO.buscarPorCodigo(id);
    }
    
}
