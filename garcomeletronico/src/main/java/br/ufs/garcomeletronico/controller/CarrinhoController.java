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

import br.ufs.garcomeletronico.dao.MesaDAO;
import br.ufs.garcomeletronico.dao.ProdutoDAO;
import br.ufs.garcomeletronico.model.Item;
import br.ufs.garcomeletronico.model.Mesa;
import br.ufs.garcomeletronico.model.Produto;
import br.ufs.garcomeletronico.service.CarrinhoService;
import br.ufs.garcomeletronico.service.ComandaService;


@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    private CarrinhoService carrinhoService;
    private ComandaService comandaService;

    public CarrinhoController(CarrinhoService carrinhoService,ComandaService comandaService){

        this.carrinhoService = carrinhoService;
        this.comandaService = comandaService;

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

    @GetMapping("/valorTotal")
    public String valorTotal(HttpServletRequest request) {
        
        return carrinhoService.valorTotal(request);
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

    @DeleteMapping("/limpar")
    public void limpar(HttpServletResponse response){
        carrinhoService.limpar(response);
    }

    // Finaliza o carrinho virando comanda/pedido
    @PostMapping("/finalizar")
    public void finalizarCarrinho(HttpServletRequest request,
                                  HttpServletResponse response) {
        
        String comandaId = comandaService.resgatarId(request);

        carrinhoService.finalizar(request, response, comandaId);
    }
}
    
