package br.ufs.garcomeletronico.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import br.ufs.garcomeletronico.dao.ComandaDAO;
import br.ufs.garcomeletronico.dao.MesaDAO;
import br.ufs.garcomeletronico.dao.ProdutoDAO;
import br.ufs.garcomeletronico.model.Comanda;
import br.ufs.garcomeletronico.model.Item;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.service.CarrinhoService;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    private CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService){

        this.carrinhoService = carrinhoService;

    }

    // Inicia o carrinho com uma mesa
    @PostMapping("/iniciar/{mesaId}")
    public void iniciarCarrinho(@PathVariable String mesaId, HttpServletResponse response) {
        MesaDAO mesaDAO = new MesaDAO();
        Mesa mesa = mesaDAO.buscarPorCodigo(mesaId); 
        
        if (mesa == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return;
        }
        carrinhoService.iniciar(response, mesa);
    }

    @GetMapping
    public List<Item> listar(HttpServletRequest request) {
    return carrinhoService.listar(request);
    }

    // Adiciona produto ao carrinho
    @PostMapping("/adicionar/{produtoId}")
    public void adicionarProduto(@PathVariable String produtoId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        System.out.println("Post recebido");
        ProdutoDAO produtoDAO = new ProdutoDAO();
                                
        Produto produto = produtoDAO.buscarPorCodigo(produtoId); 
        carrinhoService.adicionar(request, response, produto);
    }

    // Remove produto do carrinho
    @DeleteMapping("/remover/{produtoId}")
    public void removerProduto(@PathVariable String produtoId,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
                                
        Produto produto = produtoDAO.buscarPorCodigo(produtoId);
        carrinhoService.remover(request, response, produto);
    }

    // Finaliza o carrinho virando comanda/pedido
    @PostMapping("/finalizar/{comandaId}")
    public void finalizarCarrinho(@PathVariable String comandaId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        

        carrinhoService.finalizar(request, response, comandaId);
    }
}
    
